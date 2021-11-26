<template id="play">
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
              <button type="submit" v-on:click="reset">Reset board</button>
              <button type="submit" v-on:click="ownMove">Eigen zet</button>
              <button type="submit" v-on:click="computerMove">Computer zet</button>
                    van: <input v-model="van" >
                    naar: <input v-model="naar" >
       </span>
      <br>

    </div>
  </app-frame>
</template>
<script>



Vue.component("play", {
  template: "#play",
  data: () => ({
    status: null,
    van:  "" ,
    naar: "",
    rows: [
      { id:21 },
      { id:20 },
      { id:8 },
      { id:7 },
      { id:6 },
      { id:5 },
      { id:4 },
      { id:3 },
      { id:2 },
      { id:1 },
      { id:11 },
      { id:10 }
    ],

    cols: [
      { id: 'A' },
      { id: 'B' },
      { id: 'C' },
      { id: 'D' },
      { id: 'E' },
      { id: 'F' },
      { id: 'G' },
      { id: 'H' },
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
      window.location.href = "/status";
    },
    next: function (event) {
      window.location.href = "/demo";
    },
    reset: function (event) {
      fetch(`/api/game/reset`)
      .catch(() => alert("Error"));
    },
    computerMove: function (event) {
      fetch(`/api/game/computermove`)
      .catch(() => alert("Error"));
    },
    ownMove: function (event) {
      fetch(`/api/game/ownmove/`+this.van+'/'+this.naar)
      .catch(() => alert("Error"));
    },
    move: function (vlak) {
      if (this.van==""){
        this.van = vlak
      }
      else{
        if (this.naar==""){
          this.naar = vlak
        }
        else{
          this.van = vlak
          this.naar = ""
        }
      }
    },
  }
});

$(document).ready(function () {
  $("#statusPrev").click(function () {
    window.location.href = "/status";
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
