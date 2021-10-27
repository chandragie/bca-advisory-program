import http from "../http-common";

class TodoDataService {
  getTodos() {
    return http.get("/todo?userId=" + localStorage.getItem("username").replace(/["]+/g, ''));
  }

  addTodo(todo) {
    return http.post('/todo', todo);
  }

  updateTodo(id, done) {
    return http.put('/todo', {
      id: id,
      done: done,
      updatedBy: localStorage.getItem("username").replace(/["]+/g, '')
    });
  }

}

export default new TodoDataService();