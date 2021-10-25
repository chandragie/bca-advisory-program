<template>
  <div>
    <Header></Header>
    <div class="flex justify-center pt-16">
      <div class="w-1/2 mt-7">
        <div class="flex flex-col justify-center items-center w-full">
          <form
            autocomplete="off"
            @submit.prevent="handleSubmit"
            class="w-full"
          >
            <div class="flex items-center">
              <div class="w-1/3">
                <label
                  class="block text-gray-500 font-bold text-left"
                  for="inline-full-name"
                >
                  What do you want to do?
                </label>
              </div>
              <div class="w-2/3">
                <input
                  class="
                    bg-gray-100
                    appearance-none
                    border-2 border-gray-200
                    rounded
                    w-full
                    py-2
                    px-4
                    text-gray-700
                    leading-tight
                    focus:outline-none focus:bg-white focus:border-indigo-500
                  "
                  id="inline-full-name"
                  type="text"
                  placeholder="Do something..."
                  v-model="todo"
                  v-validate="'required|min:6|max:100'"
                  name="todo"
                />
                <label v-if="errors.has('todo')" class="text-sm text-red-700">{{
                  errors.first("todo")
                }}</label>
              </div>
            </div>
            <div class="flex justify-between w-full mt-10">
              <button
                type="button"
                @click.prevent="handleBack"
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
                Cancel
              </button>

              <button
                type="submit"
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
                Submit
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import todoDS from "../../services/TodoDataService";
import Header from "../../components/header/Header.vue";
export default {
  title: "Add todo",
  components: {
    Header,
  },
  data() {
    return { todo: "" };
  },
  methods: {
    handleBack() {
      this.$router.push("/todo");
    },
    handleSubmit() {
      this.$validator.validate().then((isValid) => {
        if (isValid) {
          todoDS
            .addTodo({ title: this.todo })
            .then((response) => {
              if (response.status == 200) {
                this.$router.push("/todo");
              }
            })
            .catch((error) => {
              console.log(error);
              return Promise.reject(error);
            });
        }
      });
    },
  },
};
</script>

<style>
</style>