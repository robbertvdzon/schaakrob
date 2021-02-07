<template id="status">
  <app-frame>

    <div>

      <nav>
        <ul class="nav nav-pills pull-right">
          <li role="presentation"><a href="#" id="statusPrev"><</a></li>
          <li role="presentation"><a href="#" id="statusNext">></a></li>
        </ul>
      </nav>
      <nettoets-header activebutton="operationallog" title="Speelbord"></nettoets-header>
      <div class="myBorder">
        Speelbord
        <span>
        </span>
      </div>

      <table >
        <tbody>
        <tr v-for="row in rows">
          <td v-for="col in cols">
            <button type="submit" class="schaakbtn" v-on:click="move(col.id+row.id)">{{col.id}}{{row.id}}</button>
          </td>
        </tr>
        </tbody>
      </table>
      <br>
      <hr>
      <br>
      <span>
              <button type="submit" v-on:click="clamp">Pak</button>
              <button type="submit" v-on:click="release">Laat los</button>
       </span>
      <hr>
      <span>
              <button type="submit" v-on:click="home_vert">home vertical</button>
              <button type="submit" v-on:click="home_hor">home horizontal</button>
              <button type="submit" v-on:click="sleeping">Sleep</button>
       </span>
      <br>

    </div>
  </app-frame>
</template>
<script>



Vue.component("status", {
  template: "#status",
  data: () => ({
    status: null,
    rows: [
      { id:8 },
      { id:7 },
      { id:6 },
      { id:5 },
      { id:4 },
      { id:3 },
      { id:2 },
      { id:1 }
    ],

    cols: [
      { id: 'A' },
      { id: 'B' },
      { id: 'C' },
      { id: 'D' },
      { id: 'E' },
      { id: 'F' },
      { id: 'G' },
      { id: 'H' }
    ]
  }),
  computed: {
    "columns": function columns() {
      if (this.rows.length == 0) {
        return [];
      }
      return Object.keys(this.rows[0])
    }
  },

  created() {
    this.load()
  },
  methods: {
    load: function (event) {
    },
    prev: function (event) {
      window.location.href = "/home";
    },
    next: function (event) {
      window.location.href = "/demo";
    },
    move: function (vlak) {
      axios.post(`/api/movevlak`, vlak)
      .then(res => {
      })
      .catch(error => {
        alert("Error")
      })
    },
    home_vert: function (event) {
      fetch(`/api/home_vert`)
      .catch(() => alert("Error"));
    },
    home_hor: function (event) {
      fetch(`/api/home_hor`)
      .catch(() => alert("Error"));
    },
    sleeping: function (event) {
      fetch(`/api/sleep`)
      .catch(() => alert("Error"));
    },
    clamp: function (event) {
      fetch(`/api/clamp`)
      .catch(() => alert("Error"));
    },
    release: function (event) {
      fetch(`/api/release`)
      .catch(() => alert("Error"));
    },


  }
});

$(document).ready(function () {
  $("#statusPrev").click(function () {
    window.location.href = "/manual";
  });
  $("#statusNext").click(function () {
    window.location.href = "/demo";
  });
});

</script>
<style scoped>
.myBorder {
  color: white;
  background-color: #4d6b85;
}
.schaakbtn {
  width: 40px;
  height: 40px;
  min-width: w0px;
}
</style>
