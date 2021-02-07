<template id="demo">
  <app-frame>
    <div>

      <nav>
        <ul class="nav nav-pills pull-right">
          <li role="presentation"><a href="#" id="demoPrev"><</a></li>
          <li role="presentation"><a href="#" id="demoNext">></a></li>
        </ul>
      </nav>


      <nettoets-header activebutton="operationallog" title="Demo"></nettoets-header>
      <div class="myBorder">
        Demo
        <span>
        </span>
      </div>
      <textarea v-model="demo" rows="10" cols="20"></textarea>
      <button type="submit" v-on:click="saveDemo">saveDemo</button>
      <br><br>
      <button type="submit" v-on:click="startDemoOnce">start once</button>
      <button type="submit" v-on:click="startDemoLoop">start in loop</button>
      <button type="submit" v-on:click="stopDemo">stop demo</button>
      <br>
    </div>
  </app-frame>
</template>
<script>
Vue.component("demo", {
  template: "#demo",
  data: () => ({
    demo: null,
  }),
  created() {
    this.load()
  },
  methods: {
    load: function (event) {
      fetch(`/api/demo`)
      .then(res => res.text())
      .then(text => this.demo=text)
      .catch(() => alert("Error"));
    },
    saveDemo: function (event) {
      axios.post(`/api/demo`, this.demo)
      .then(res => {
      })
      .catch(error => {
        alert("Error")
      })
    },
    startDemoOnce: function (event) {
      fetch(`/api/startdemoonce`)
      .catch(() => alert("Error"));
    },
    startDemoLoop: function (event) {
      fetch(`/api/startdemoloop`)
      .catch(() => alert("Error"));
    },
    stopDemo: function (event) {
      fetch(`/api/stopdemo`)
      .catch(() => alert("Error"));
    }
  }
});

$(document).ready(function () {
  $("#demoPrev").click(function () {
    window.location.href = "/status";
  });
  $("#demoNext").click(function () {
    window.location.href = "/rebuild";
  });
});

</script>
<style scoped>
.myBorder {
  color: white;
  background-color: #4d6b85;
}

</style>
