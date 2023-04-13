

const url = 'http://localhost:8080/api/users/'
const urlDelete = 'http://localhost:8080/api/delete/'
const urlUpdate = 'http://localhost:8080/api/update/'
const urlCreate = 'http://localhost:8080/api/adduser/'

const insert = document.querySelector('tbody')
let result = ''

const modalElement = new bootstrap.Modal(document.getElementById('modalElement'))
const formElement = document.querySelector('form')
//const id = document.getElementById('id')
const firstname = document.getElementById('firstname')
const lastname = document.getElementById('lastname')
const age = document.getElementById('age')
const email = document.getElementById('email')
const password = document.getElementById('password')
const role = document.getElementById('sel')
let option = ''


 const form = document.getElementById('r');
 const formData = new FormData(form);
let currentRoles = [];
const roles4add = Array.from(formData.getAll('roles'))

class Role {
    constructor(id, role) {
        this.id = id
        this.role = role
    }

}

for(let i = 0; i < roles4add.length; i++) {
    const id = roles4add[i];
    const role = id == 1 ? 'ROLE_ADMIN' : 'ROLE_USER'
    currentRoles.push(new Role(id, role))
}
// const user = {
//     firstname: formData.get(`firstname`),
//     lastname: formData.get(`lastname`),
//     age: formData.get('age'),
//     email: formData.get(`email`),
//     password: formData.get(`password`),
//     role: currentRoles
// };






btnCreate.addEventListener('click', ()=>{

    firstname.value = ''
    lastname.value = ''
    age.value = ''
    email.value = ''
    password.value = ''
    role.value = ''
    modalElement.show()
    option = 'create'
})

const getUsers = (users) => {
    users.forEach(user => {
        result += `
            <tr>
                <td>${user.id}</td>
                <td>${user.firstname}</td>
                <td>${user.lastname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.rolesAsString}</td>
                <td class="text-center"> <a class="btnEdit btn btn-primary">Edit</a>
                    <a class="btnDelete btn btn-danger">Delete</a></td>
        </tr>`
    })
    insert.innerHTML = result
}
fetch(url)
    .then(response => response.json())
    .then(user => getUsers(user))
    .catch(error => console.log(error))


const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
    if(e.target.closest(selector)) {
        handler(e)
        }
    })
}

on (document, 'click', '.btnDelete', e => {
    const file = e.target.parentNode.parentNode
    const id = file.firstElementChild.innerHTML
    alertify.confirm("Tochno udalit?",
        function(){
        fetch(url + id, {
            method: 'DELETE'
        })
            .then(res => res.json())
            .then( () => location.reload)
        },
        function() {
            alertify.error('close')
        })
})

let idForm = 0
on (document, 'click', '.btnEdit', e => {
    const file = e.target.parentNode.parentNode
    idForm = file.children[0].innerHTML
    const firstnameForm = file.children[1].innerHTML
    const lastnameForm = file.children[2].innerHTML
    const ageForm = file.children[3].innerHTML
    const emailForm = file.children[4].innerHTML
    const passwordForm = file.children[6].innerHTML
    //const roleForm = file.children[5].innerHTML


    firstname.value = firstnameForm
    lastname.value = lastnameForm
    age.value = ageForm
    email.value = emailForm
    password.value = passwordForm
    role.value = currentRoles
    option = 'edit'
    modalElement.show()
})

formElement.addEventListener('submit', (e) => {
    e.preventDefault()
    if(option == 'create'){
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-type':'application/json'
            },
             body: JSON.stringify(
            {

                firstname: firstname.value,
                lastname: lastname.value,
                age: age.value,
                email: email.value,
                password: password.value,
                role: role.value
            })
        })
            .then( response => response.json() )
            .then( user => {
                const newElement = []
                newElement.push(user)
                getUsers(newElement)
            })
    }
    if(option == 'edit') {
        fetch(url + idForm, {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(
                {
                id: idForm,
                firstname: firstname.value,
                lastname: lastname.value,
                age: age.value,
                email: email.value,
                password: password.value,
                role: role.value
            })
        })
            .then(response => response.json())
            //.then(response => location.reload())
    }
    modalElement.hide()
})
