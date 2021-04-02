<template id="manual">
  <app-frame>

    <div>

      <nav>
        <ul class="nav nav-pills pull-right">
          <li role="presentation"><a href="#" id="manualPrev"><</a></li>
          <li role="presentation"><a href="#" id="manualNext">></a></li>
        </ul>
      </nav>

      <nettoets-header activebutton="operationallog" title="Manual"></nettoets-header>
      <div class="myBorder">
        Manual actions
        <span>
        </span>
      </div>
      <span>
              <button type="submit" v-on:click="home_vert">home vertical</button>
              <button type="submit" v-on:click="home_hor">home horizontal</button>
              <button type="submit" v-on:click="sleeping">Sleep</button>
              <button type="submit" v-on:click="bootsound">Bootsound</button>
       </span>
      <br>
      <span>
              <button type="submit" v-on:click="clamp">Pak</button>
              <button type="submit" v-on:click="release">Laat los</button>
       </span>
      <br>
      <span>
              <button type="submit" v-on:click="hold">hold</button>
              <button type="submit" v-on:click="drop">drop</button>
              <button type="submit" v-on:click="activate">activate</button>
              <button type="submit" v-on:click="deactivate">deactivate</button>
       </span>
      <br>
      <br>
      Naar vlak:
      <input v-model="vlak" >
      <button type="submit" v-on:click="movevlak">naar vlak</button>
      <br>
      <br>
      Manual pos:
      <input v-model="pos" >
      <button type="submit" v-on:click="move">move</button>
      <br>
      <br>
      A8:
      <input v-model="a8pos" >
      <button type="submit" v-on:click="saveA8">Save</button>
      <br>
      H1:
      <input v-model="h1pos" >
      <button type="submit" v-on:click="saveH1">Save</button>
      <br>
      A11:
      <input v-model="a11pos" >
      <button type="submit" v-on:click="saveA11">Save</button>
      <br>
      H10:
      <input v-model="h10pos" >
      <button type="submit" v-on:click="saveH10">Save</button>
      <br>
      A21:
      <input v-model="a21pos" >
      <button type="submit" v-on:click="saveA21">Save</button>
      <br>
      H20:
      <input v-model="h20pos" >
      <button type="submit" v-on:click="saveH20">Save</button>
      <br>
      Snelheid:
      <input v-model="snelheid" >
      <button type="submit" v-on:click="saveSnelheid">Save</button>
      <br>
      <br>
      Delay na pak:
      <input v-model="delaynapak" >
      <button type="submit" v-on:click="saveDelayNaPak">Save</button>
      <br>
      <br>
      Delay na zet:
      <input v-model="delaynazet" >
      <button type="submit" v-on:click="saveDelayNaZet">Save</button>
      <br>

    </div>
  </app-frame>
</template>
<script>
Vue.component("manual", {
  template: "#manual",
  data: () => ({
    manual: null,
    pos:"1000,1000",
    a8pos:"",
    a21pos:"",
    a11pos:"",
    h1pos:"",
    h10pos:"",
    h20pos:"",
    snelheid:"2.0",
    delaynapak:"2.0",
    delaynazet:"2.0",
    vlak:"A8"
  }),
  created() {
    this.load()
  },
  methods: {
    load: function (event) {
      fetch(`/api/a8`)
      .then(res => res.text())
      .then(text => this.a8pos=text)
      .catch(() => alert("Error"));

      fetch(`/api/a21`)
      .then(res => res.text())
      .then(text => this.a21pos=text)
      .catch(() => alert("Error"));

      fetch(`/api/a11`)
      .then(res => res.text())
      .then(text => this.a11pos=text)
      .catch(() => alert("Error"));

      fetch(`/api/h1`)
      .then(res => res.text())
      .then(text => this.h1pos=text)
      .catch(() => alert("Error"));

      fetch(`/api/h10`)
      .then(res => res.text())
      .then(text => this.h10pos=text)
      .catch(() => alert("Error"));

      fetch(`/api/h20`)
      .then(res => res.text())
      .then(text => this.h20pos=text)
      .catch(() => alert("Error"));

      fetch(`/api/snelheid`)
      .then(res => res.text())
      .then(text => this.snelheid=text)
      .catch(() => alert("Error"));

      fetch(`/api/delaynapak`)
      .then(res => res.text())
      .then(text => this.delaynapak=text)
      .catch(() => alert("Error"));

      fetch(`/api/delaynazet`)
      .then(res => res.text())
      .then(text => this.delaynazet=text)
      .catch(() => alert("Error"));

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
    hold: function (event) {
      fetch(`/api/hold`)
      .catch(() => alert("Error"));
    },
    drop: function (event) {
      fetch(`/api/drop`)
      .catch(() => alert("Error"));
    },
    activate: function (event) {
      fetch(`/api/activate`)
      .catch(() => alert("Error"));
    },
    deactivate: function (event) {
      fetch(`/api/deactivate`)
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
    bootsound: function (event) {
      fetch(`/api/bootsound`)
      .catch(() => alert("Error"));
    },
    move: function (event) {
      axios.post(`/api/move`, this.pos)
      .then(res => {
      })
      .catch(error => {
        alert("Error")
      })
    },
    movevlak: function (event) {
      axios.post(`/api/movevlak`, this.vlak)
      .then(res => {
      })
      .catch(error => {
        alert("Error")
      })
    },
    saveA8: function (event) {
      axios.post(`/api/a8`, this.a8pos)
      .then(res => {
      })
      .catch(error => {
        alert("Error")
      })
    },
    saveA11: function (event) {
      axios.post(`/api/a11`, this.a11pos)
      .then(res => {
      })
      .catch(error => {
        alert("Error")
      })
    },
    saveA21: function (event) {
      axios.post(`/api/a21`, this.a21pos)
      .then(res => {
      })
      .catch(error => {
        alert("Error")
      })
    },
    saveH1: function (event) {
      axios.post(`/api/h1`, this.h1pos)
      .then(res => {
      })
      .catch(error => {
        alert("Error")
      })
    },
    saveH10: function (event) {
      axios.post(`/api/h10`, this.h10pos)
      .then(res => {
      })
      .catch(error => {
        alert("Error")
      })
    },
    saveH20: function (event) {
      axios.post(`/api/h20`, this.h20pos)
      .then(res => {
      })
      .catch(error => {
        alert("Error")
      })
    },
    saveSnelheid: function (event) {
      axios.post(`/api/snelheid`, this.snelheid)
      .then(res => {
      })
      .catch(error => {
        alert("Error")
      })
    },
    saveDelayNaPak: function (event) {
      axios.post(`/api/delaynapak`, this.delaynapak)
      .then(res => {
      })
      .catch(error => {
        alert("Error")
      })
    },
    saveDelayNaZet: function (event) {
      axios.post(`/api/delaynazet`, this.delaynazet)
      .then(res => {
      })
      .catch(error => {
        alert("Error")
      })
    },

  }
});

$(document).ready(function () {
  $("#manualPrev").click(function () {
    window.location.href = "/rebuild";
  });
  $("#manualNext").click(function () {
    window.location.href = "/status";
  });
});

</script>
<style scoped>
.myBorder {
  color: white;
  background-color: #4d6b85;
}

</style>
