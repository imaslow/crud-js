

const url = 'http://localhost:8080/api/users'
const insert = document.querySelector('tbody')
let result = ''

const modalElement = new bootstrap.Modal(document.getElementById('modalElement'))
const formElement = document.querySelector('form')
const id = document.getElementById('id')
const firstname = document.getElementById('firstname')
const lastname = document.getElementById('lastname')
const age = document.getElementById('age')
const email = document.getElementById('email')
const role = document.getElementById('role')
let option = ''

btnCreate.addEventListener('click', ()=>{
    id.value = ''
    firstname.value = ''
    lastname.value = ''
    age.value = ''
    email.value = ''
    role.value = ''
    modalElement.show()
    option = 'create'
})
fetch(url)
    .then(response => response.json())
    .then(user => getUsers(user))
    .catch(error => console.log(error))

const getUsers = (users) => {
    users.forEach(user => {
        result +=
            <tr>
                <td>${user.id}</td>
                <td>${user.firstname}</td>
                <td>${user.lastname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td className="text-centr"> <a className="btnEdit btn btn-primary">Edit</a>
                    <a className="btnDelete btn btn-danger">Delete</a></td>
        </tr>
    })
    insert.innerHTML = result
}

