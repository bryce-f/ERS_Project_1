
document.getElementById('amount').value = localStorage.getItem('amount');

document.getElementById('selection').value = localStorage.getItem('type');

document.getElementById('desc').value = localStorage.getItem('desc');

var reimbId  = localStorage.getItem('reimbId');
const userRole = localStorage.getItem('user_role');
const userId = localStorage.getItem('user_id');

var cancelBtn = document.querySelector('#cancel-btn');
var selectElem = document.getElementById('selection');
let subBtn = document.getElementById('submit-new-btn');

cancelBtn.addEventListener('click', () => {
    window.history.go(-1);
});

subBtn.addEventListener('click', () =>{
    submitInfo();
});

const URL = `http://localhost:8081/users/${userId}/reimbursements/${reimbId}`



async function submitInfo() {
        //const userId = localStorage.getItem('user_id');
        const amount = document.getElementById('amount').value;
        const type = selectElem.selectedIndex + 1;
        const desc = document.getElementById('desc').value;
        const receipt = document.getElementById('input-image').files;
        const formData = new FormData();
        formData.append('receipt', receipt[0]);
        formData.append('amount', amount);
        formData.append('type', type);
        formData.append('desc', desc)
        // console.log(formData.entries());
        // console.log(formData.keys);
        console.log(localStorage);

        for(var pair of formData.entries()) {
        console.log(pair[0]+', '+pair[1]);
    }

    
    let res = await fetch(URL, {
        method: 'PUT',
        headers: {
        'Authorization': `Bearer ${localStorage.getItem('jwt')}` // Include our JWT into the request
        },
        body: formData
    })
    console.log(res.status);
    console.log("role " + userRole);
     if (res.status === 201) {
         

         if (userRole == 1) {
            window.location = '/admin.html';
        } else if (userRole == 2) {
            window.location = '/employee.html';
        }
    } else {
        alert("Submission failed. Note: Image is mandatory. Please try again or contact your administrator")
        // window.history.go(-1);
    }
}