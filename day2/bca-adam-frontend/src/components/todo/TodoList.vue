<template>
  <div>
    <Header></Header>
    <div class="flex justify-center pt-16">
      <div class="w-1/2 mt-7">
        <div class="flex flex-col justify-center items-center w-full">
          <div class="flex justify-between w-full">
            <button
              type="button"
              @click.prevent="handleAdd"
              class="
                w-32
                rounded-md
                bg-indigo-400
                hover:bg-indigo-300
                p-2
                text-md
                focus:outline-none
                text-white
                font-semibold
                mb-3
              "
            >
              Add to do
            </button>

            <button
              type="button"
              @click.prevent="handleLogout"
              class="
                w-32
                rounded-md
                bg-indigo-400
                hover:bg-indigo-300
                p-2
                text-md
                focus:outline-none
                text-white
                font-semibold
                mb-3
              "
            >
              Logout
            </button>
          </div>

          <!-- Cards -->
          <ul
            v-if="todos.length"
            role="list"
            class="
              border border-gray-200
              rounded-md
              divide-y divide-gray-200
              w-3/4
            "
          >
            <li
              class="pl-3 pr-4 py-3 flex items-center justify-between text-lg"
              v-for="todo in todos"
              :key="todo.id.toString()"
            >
              <div class="flex-1 flex items-center">
                <p
                  class="text-gray-600"
                  aria-hidden="true"
                  v-bind:class="[todo.done ? 'line-through' : '']"
                >
                  {{ todo.title }}
                </p>
              </div>
              <div class="ml-4 flex-shrink-0">
                <button
                  type="button"
                  v-if="!todo.done"
                  @click="handleUpdate(todo.id, true)"
                  class="font-medium text-indigo-600 hover:text-indigo-400"
                >
                  Done
                </button>
                <button
                  type="button"
                  v-if="todo.done"
                  @click="handleUpdate(todo.id, false)"
                  class="font-medium text-gray-600 hover:text-gray-500"
                >
                  Undone
                </button>
              </div>
            </li>
          </ul>

          <h1 class="font-bold text-3xl text-gray-600" v-else>
            You're all caught up!
          </h1>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import todoDS from "../../services/TodoDataService";
import Header from "../../components/header/Header.vue";
export default {
  title: "Todo List",
  components: {
    Header,
  },
  data() {
    return {
      todos: [
        {
          id: String,
          title: String,
          createdDate: Date,
          modifiedDate: Date,
          done: Boolean,
        },
      ],
    };
  },
  methods: {
    getAllTodos() {
      todoDS
        .getTodos()
        .then((response) => {
          this.todos = response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handleLogout() {
      this.$store
        .dispatch("auth/logout")
        .then(() => {
          this.$router.push("/login");
        })
        .catch((error) => {
          console.log("error accepted! " + error);
        });
    },
    handleAdd() {
      this.$router.push("/todo/add");
    },
    handleUpdate(id, done) {
      console.log(id);
      todoDS
        .updateTodo(id, done)
        .then((response) => {
          if (response.status == 200) {
            this.getAllTodos();
          }
        })
        .catch((error) => {
          console.log(error);
          return Promise.reject(error);
        });
    },
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  mounted() {
    if (this.loggedIn) {
      this.getAllTodos();
    }
  },
};
</script>

<style></style>
