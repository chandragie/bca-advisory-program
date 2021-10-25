import http from "../http-common";

class UserDataService {
    register(user) {
        return http.post('/user', {
            username: user.username,
            name: user.name,
            password: user.password
        });
    }
}



export default new UserDataService();