import http from "../http-common";

class TodoDataService {
  getTodos() {
    return http.get("/todo");
  }

  addTodo(todo) {
    return http.post('/todo', todo);
  }

  updateTodo(id, done) {
    return http.put('/todo', {
      id: id,
      done: done
    });
  }

}

export default new TodoDataService();