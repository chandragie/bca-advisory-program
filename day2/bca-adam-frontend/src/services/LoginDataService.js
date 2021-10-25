import http from "../http-common";

class LoginDataService {

    login(user) {
        return http.post('/sign/in', user).then(response => {
            if (response.status == 200) {
                localStorage.setItem('token', JSON.stringify(response.data));
                localStorage.setItem(
                    "username",
                    JSON.stringify(user.username));
            }
            return response.status;
        }).catch(error => {
            console.log(error);
            return Promise.reject(error);
        });
    }

    logout() {
        return http.post('/sign/out', {
            username: JSON.parse(localStorage.getItem('username'))
        }).then(response => {
            if (response.status == 200) {
                localStorage.removeItem('token');
                localStorage.removeItem('username');
                return response;
            }
        }, error => {
            console.log(error);
            return Promise.reject(error);
        });
    }
}

export default new LoginDataService();