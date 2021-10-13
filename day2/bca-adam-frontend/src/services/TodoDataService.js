import http from "../http-common";

class BookDataService {
  getBooks() {
    return http.get("/todo");
  }
}

export default new BookDataService();
