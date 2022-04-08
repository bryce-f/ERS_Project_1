let logoutBtn = document.querySelector('#logout-btn');
let newReimBtn = document.querySelector('#new-btn');
// var search = document.querySelector('#reimbursements-tbl_filter')
var pbtn = document.querySelector('#sort-pending');
var abtn = document.querySelector('#sort-approved');
var dbtn = document.querySelector('#sort-denied');
var cbtn = document.querySelector('#clear-sort');

pbtn.addEventListener('click',() => {
    var table = $('#reimbursements-tbl').DataTable();
 // #myInput is a <input type="text"> element
    
    table.search( "pending" ).draw();
    } );;

abtn.addEventListener('click',() => {
    var table = $('#reimbursements-tbl').DataTable();
 // #myInput is a <input type="text"> element
    
    table.search( "approved" ).draw();
});;

dbtn.addEventListener('click',() => {
    var table = $('#reimbursements-tbl').DataTable();
 // #myInput is a <input type="text"> element
    
    table.search( "denied" ).draw();
});;

cbtn.addEventListener('click',() => {
    var table = $('#reimbursements-tbl').DataTable();
 // #myInput is a <input type="text"> element
    
    table.search( "" ).draw();
});;

newReimBtn.addEventListener('click', () => {
    window.location = '/newReimbursement.html';
})


logoutBtn.addEventListener('click', () => {
    localStorage.removeItem('jwt');

    window.location = '/index.html';
});

window.addEventListener('load', (event) => {

    populateReimbursementsTable();
});

async function populateReimbursementsTable() {
    const URL = `http://localhost:8081/reimbursements`;
    console.log(URL)
    let res = await fetch(URL, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}` // Include our JWT into the request
        }

    })

    if (res.status === 200) {
        let reimbursements = await res.json();
        count = 1
        for (let reimbursement of reimbursements) {
            let tr = document.createElement('tr');
            tr.setAttribute('class', 'row');
            tr.setAttribute('id', 'rowId-' + count)
            
            // let authorId = reimbursement.authorId;

            let rIdCol = document.createElement('td');
            rIdCol.innerText = reimbursement.reimbId;
            rIdCol.setAttribute('class', 'reimbId');

            let firstCol = document.createElement('td');
            firstCol.innerText = reimbursement.firstName;
            firstCol.setAttribute('class', 'firstName');

            let lastCol = document.createElement('td');
            lastCol.innerText = reimbursement.lastName;
            lastCol.setAttribute('class', 'lastName');


            let amountCol = document.createElement('td');
            amountCol.innerText = reimbursement.reimbAmount;
            amountCol.setAttribute('class', 'amount');

            let statusCol = document.createElement('td');
            statusCol.setAttribute('class', 'status');
            
            
            if (reimbursement.reimbStatus == 'pending'){
                var statusColor = 'blue'
            }
            else if (reimbursement.reimbStatus == 'approved') {
                var statusColor = 'green'
            }
            else if (reimbursement.reimbStatus == 'denied') {
                var statusColor = 'red'
            }
            else {
                var statusColor = 'black'
            }

            statusCol.innerText = reimbursement.reimbStatus;
            statusCol.style.color = statusColor;

            let typeCol = document.createElement('td');
            typeCol.innerText = reimbursement.reimbType;
            typeCol.setAttribute('class', 'type');

            let descCol = document.createElement('td');
            descCol.innerText = reimbursement.reimbDesc;
            descCol.setAttribute('class', 'description');

            let subDateCol = document.createElement('td');
            subDateCol.innerText = reimbursement.reimbSubmitted;
            subDateCol.setAttribute('class', 'submitted');
            

            let resDateCol = document.createElement('td');
            resDateCol.innerText = reimbursement.reimbResolved;
            resDateCol.setAttribute('class', 'resolved');

            let resolverCol = document.createElement('td');
            resolverCol.setAttribute('class', 'resolver');
            console.log(reimbursement.reimbResolver);

            if (reimbursement.reimbResolver == 0 || reimbursement.reimbResolver == null) {
                resolverCol.innerText = "";
            } else {
                resolverCol.innerText = reimbursement.resolverFirst + " " + reimbursement.resolverLast;
            }

            let receiptCol = document.createElement('td'); // fix this for image input preview
            receiptCol.setAttribute('class', 'receipt');

            let rec_img = document.createElement('img');
            rec_img.src = reimbursement.receiptUrl;
            rec_img.id = 'pic-' + count;
            rec_img.addEventListener('click', function () {previewPic(reimbursement.receiptUrl)})
            
            receiptCol.appendChild(rec_img);

            // let td12 = document.createElement('td');
            // td12.setAttribute('class','fileInput');
            // let uploadBtn = document.createElement('input');
            // uploadBtn.type = 'file';
            // uploadBtn.className = 'upload-input';
            // uploadBtn.id = 'upload-input';
            // // uploadBtn.value = 'Upload';
            // td12.appendChild(uploadBtn);

            let approveCol = document.createElement('td');
            approveCol.setAttribute('class','approveCol')

            let denyCol = document.createElement('td');
            denyCol.setAttribute('class','denyCol')

            
            let approveBtn = document.createElement('input');
            approveBtn.type = 'button';
            approveBtn.className = 'button is-small is-success is-outlined';
            approveBtn.id = 'approve-button';
            approveBtn.value = 'Approve';
            approveBtn.setAttribute('data-target', 'modal-js-example');
            approveBtn.addEventListener('click', function () {changeReimbursementStatus(reimbursement.reimbId, "approved")});
            
            
            
            let denyBtn = document.createElement('input');
            denyBtn.type = 'button';
            denyBtn.className = 'button is-small is-danger is-outlined';
            denyBtn.id = 'deny-button'
            denyBtn.value = 'Deny'
            denyBtn.addEventListener('click', function () {changeReimbursementStatus(reimbursement.reimbId, "denied")});

            console.log(reimbursement.reimbAuthorId);
            console.log(localStorage.getItem('user_id'));
            if (reimbursement.reimbStatus == 'pending' && reimbursement.reimbAuthorId != localStorage.getItem('user_id')){
                approveCol.appendChild(approveBtn);
                denyCol.appendChild(denyBtn);
            }

            let deleteCol = document.createElement('td');
            let deleteBtn = document.createElement('input');
            deleteCol.setAttribute('class', 'deleteRow')
            deleteBtn.type = 'button';
            deleteBtn.className = 'delete-button button is-danger is-small';
            deleteBtn.id = 'delete-button';
            deleteBtn.value = 'Delete';
            deleteBtn.style.color = 'white';            
            deleteCol.appendChild(deleteBtn);
            
            deleteBtn.addEventListener('click', function () {deleteReimbursement(reimbursement.reimbId, localStorage.getItem('user_id'))})

            // deleteCol.addEventListener('click', 

            tr.appendChild(rIdCol);
            tr.appendChild(firstCol);
            tr.appendChild(lastCol);
            tr.appendChild(amountCol);
            tr.appendChild(typeCol);
            tr.appendChild(descCol);
            tr.appendChild(subDateCol);
            tr.appendChild(statusCol);
            tr.appendChild(approveCol);
            tr.appendChild(denyCol);
            tr.appendChild(resDateCol);
            tr.appendChild(resolverCol);
            tr.appendChild(receiptCol);
            
            tr.appendChild(deleteCol);

            let tbody = document.querySelector('#reimbursements-tbl > tbody');
            tbody.appendChild(tr);
            count += 1
            
        }
        $(document).ready(function () {
                $('#reimbursements-tbl').DataTable();
            });
    }
    
}

function previewPic(imgSrc){

    imgSec = document.querySelector('#img-placement');
    imgSec.src = imgSrc;
    modal = document.querySelector('#pic-modal');
    modal.classList.add('is-active');

}



async function deleteReimbursement(reimbId, userId) {
    const URL = `http://localhost:8081/users/${userId}/reimbursements/${reimbId}`;
    console.log(URL)
    let res = await fetch(URL, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}` // Include our JWT into the request
        }

    })
    if (res.status === 200) {
        window.location.reload()
        populateReimbursementsTable();
    }
}


async function changeReimbursementStatus(reimbId, newStatus){
    const URL = `http://localhost:8081/reimbursements/${reimbId}`;
    console.log(URL)


    const formData = new FormData();
    formData.append('status', newStatus);

    let res = await fetch(URL, {
        method: 'PATCH',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}` // Include our JWT into the request
        },
        body: formData

    })
    if (res.status === 201) {
        window.location.reload()
        populateReimbursementsTable();
    }
}





// async function deleteReimbursement(userId) {
//     const URL = `http://localhost:8081/users/${userId}/reimbursements`;
//     console.log(URL)
//     let res = await fetch(URL, {
//         method: 'POST',
//         headers: {
//             'Authorization': `Bearer ${localStorage.getItem('jwt')}` // Include our JWT into the request
//         }
//         body: {
            
//         }

//     })
//     if (res.status === 200) {
//         window.location.reload()
//         populateReimbursementsTable();
//     }
// }



// General Modal Controls //

document.addEventListener('DOMContentLoaded', () => {
  // Functions to open and close a modal
  function openModal($el) {
    $el.classList.add('is-active');
  }

  function closeModal($el) {
    $el.classList.remove('is-active');
  }

  function closeAllModals() {
    (document.querySelectorAll('.modal') || []).forEach(($modal) => {
      closeModal($modal);
    });
  }

  // Add a click event on buttons to open a specific modal
  (document.querySelectorAll('.js-modal-trigger') || []).forEach(($trigger) => {
    const modal = $trigger.dataset.target;
    const $target = document.getElementById(modal);
    console.log($target);

    $trigger.addEventListener('click', () => {
      openModal($target);
    });
  });

  // Add a click event on various child elements to close the parent modal
  (document.querySelectorAll('.modal-background, .modal-close, .modal-card-head .delete, .modal-card-foot .button') || []).forEach(($close) => {
    const $target = $close.closest('.modal');

    $close.addEventListener('click', () => {
      closeModal($target);
    });
  });

  // Add a keyboard event to close all modals
  document.addEventListener('keydown', (event) => {
    const e = event || window.event;

    if (e.keyCode === 27) { // Escape key
      closeAllModals();
    }
  });
});

// SIDEBAR

/* Set the width of the sidebar to 250px and the left margin of the page content to 250px */
function openNav() {
  document.getElementById("mySidebar").style.width = "250px";
  document.getElementById("main").style.marginLeft = "250px";
  document.getElementById("reimbursements-tbl_wrapper").style.marginLeft = "250px";
}

/* Set the width of the sidebar to 0 and the left margin of the page content to 0 */
function closeNav() {
  document.getElementById("mySidebar").style.width = "0";
  document.getElementById("main").style.marginLeft = "0";
  document.getElementById("reimbursements-tbl_wrapper").style.marginLeft = "0";
}