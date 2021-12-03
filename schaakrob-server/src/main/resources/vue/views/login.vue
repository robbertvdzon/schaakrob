<template id="login">
  <app-frame>

    <div>
      <nettoets-header activebutton="operationallog" title="Login"></nettoets-header>

      access code:
      <input v-on:keyup.enter="login"  v-model="accesscode" autofocus>
      <button type="submit" v-on:click="login">Login</button>



    </div>
  </app-frame>
</template>
<script>
Vue.component("login", {
  template: "#login",
  data: () => ({
      accesscode: "",
  }),
  created() {
    this.load()
  },
  methods: {
    load: function (event) {
    },
    login: function (event) {
      axios.post(`/api/login`, this.accesscode)
     .then(text => window.location.href = "/play")
      .catch(() => alert("Error"));
    },
  }
});

</script>
<style scoped>
.myBorder {
  color: white;
  background-color: #4d6b85;
}

</style>
