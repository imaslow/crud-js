function redirectingToStart() {
    let url = 'http://localhost:8080/api/users'
    fetch(url)
        .then(response => response.json())
        .then(data => fillingAll(data))
        .catch(error => console.log(error))
}
