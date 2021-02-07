<template id="rebuild">
  <app-frame>

    <div>

      <nav>
        <ul class="nav nav-pills pull-right">
          <li role="presentation"><a href="#" id="rebuildPrev"><</a></li>
          <li role="presentation"><a href="#" id="rebuildNext">></a></li>
        </ul>
      </nav>

      <nettoets-header activebutton="operationallog" title="Rebuild"></nettoets-header>
      <div class="myBorder">
        Update software
      </div>
      <span>
              <button type="submit" v-on:click="rebuildapp">rebuild</button>
        </span>
    </div>
  </app-frame>
</template>
<script>
Vue.component("rebuild", {
  template: "#rebuild",
  data: () => ({
    rebuild: null,
  }),
  created() {
    this.load()
  },
  methods: {
    load: function (event) {
    },
    rebuildapp: function (event) {
      fetch(`/api/rebuild`)
      .catch(() => alert("Error while rebuilding"));
    }
  }
});

$(document).ready(function () {
  $("#rebuildPrev").click(function () {
    window.location.href = "/demo";
  });
  $("#rebuildNext").click(function () {
    window.location.href = "/manual";
  });
});

</script>
<style scoped>
.myBorder {
  color: white;
  background-color: #4d6b85;
}

</style>
