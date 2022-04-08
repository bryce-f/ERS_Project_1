const userId = localStorage.getItem('user_id');
const userRole = localStorage.getItem('user_role');

const URL = `http://localhost:8081/users/${userId}/reimbursements`

let subBtn = document.getElementById('submit-new-btn');
var selectElem = document.getElementById('selection');
var cancelBtn = document.querySelector('#cancel-btn');

cancelBtn.addEventListener('click', () => {
    window.history.go(-1);
});

subBtn.addEventListener('click', () =>{
    submitInfo();
});

async function submitInfo() {
        //const userId = localStorage.getItem('user_id');
        const amount = document.getElementById('amount').value;
        const type = selectElem.selectedIndex + 1;
        const desc = document.getElementById('desc').value;
        const receipt = document.getElementById('input-image').files;
        const formData = new FormData();
        formData.append('file', receipt[0]);
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
        method: 'POST',
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