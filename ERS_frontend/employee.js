let logoutBtn = document.querySelector('#logout-btn');
let newReimBtn = document.querySelector('#new-btn');

newReimBtn.addEventListener('click', () => {
    window.location = '/newReimbursement.html';
})

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
});


logoutBtn.addEventListener('click', () => {
    localStorage.removeItem('jwt');

    window.location = '/index.html';
});

window.addEventListener('load', (event) => {
    populateReimbursementsTable();
});

async function populateReimbursementsTable() {
    const URL = `http://localhost:8081/users/${localStorage.getItem('user_id')}/reimbursements`;

    let res = await fetch(URL, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}` // Include our JWT into the request
        }
        // credentials: 'include' // This is if you're using HttpSession w/ JSESSIONID cookies
    })

    if (res.status === 200) {
        let reimbursements = await res.json();
        count = 1
        for (let reimbursement of reimbursements) {
            let tr = document.createElement('tr');
            tr.setAttribute('class', 'row');
            
            let rowId = 'rowId-' + count;
            // let authorId = reimbursement.authorId;
            tr.setAttribute('id', rowId)
            
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
            amountCol.setAttribute('class', 'amount ');

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

            let editCol = document.createElement('td');
            editCol.setAttribute('class','editCol') 
            let editBtn = document.createElement('input');
            editBtn.type = 'button';
            editBtn.setAttribute('class', 'button is-dark is-small is-outlined')
            editBtn.id = 'edit-button';
            editBtn.value = 'Edit';


            let deleteCol = document.createElement('td');
            let deleteBtn = document.createElement('input');
            deleteCol.setAttribute('class', 'deleteRow')
            deleteBtn.type = 'button';
            deleteBtn.className = 'delete-button button is-danger is-small';
            deleteBtn.id = 'delete-button';
            deleteBtn.value = 'Delete';
            deleteBtn.style.color = 'white';            
        
            
            if (statusColor == 'blue'){
                editCol.appendChild(editBtn);
                deleteCol.appendChild(deleteBtn);
                editBtn.addEventListener('click', function () {editReimbursement(rowId)})
                deleteBtn.addEventListener('click', function () {deleteReimbursement(reimbursement.reimbId, localStorage.getItem('user_id'))})
            }
            

            tr.appendChild(rIdCol);
            tr.appendChild(firstCol);
            tr.appendChild(lastCol);
            tr.appendChild(amountCol);
            tr.appendChild(statusCol);
            tr.appendChild(typeCol);
            tr.appendChild(descCol);
            tr.appendChild(subDateCol);
            tr.appendChild(resDateCol);
            tr.appendChild(resolverCol);
            tr.appendChild(receiptCol);
            tr.appendChild(editCol);
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

function editReimbursement(rowId) {
    localStorage.removeItem('amount');
    localStorage.removeItem('type');
    localStorage.removeItem('desc');
    localStorage.removeItem('reimbId');

    let amount = document.getElementById(rowId).getElementsByClassName("amount")[0].textContent;
    let type = document.getElementById(rowId).getElementsByClassName("type")[0].textContent;
    let desc = document.getElementById(rowId).getElementsByClassName("description")[0].textContent;
    let reimbId = document.getElementById(rowId).getElementsByClassName("reimbId")[0].textContent;

    localStorage.setItem('amount', amount);
    localStorage.setItem('type', type);
    localStorage.setItem('desc', desc);
    localStorage.setItem('reimbId', reimbId);

    window.location = '/editReimbursement.html';
}











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


