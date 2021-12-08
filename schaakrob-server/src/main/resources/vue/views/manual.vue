<template id="manual">
  <app-frame>

    <div>

      <nav>
            <button type="submit" v-on:click="close">Terug</button>
      </nav>

      <br>

      <span>
              <button type="submit" v-on:click="home_vert">home vertical</button>
              <button type="submit" v-on:click="home_hor">home horizontal</button>
              <button type="submit" v-on:click="sleeping">Sleep</button>
              <button type="submit" v-on:click="bootsound">Bootsound</button>
       </span>
      <br>
      <span>
              <button type="submit" v-on:click="clamp1">Pak1</button>
              <button type="submit" v-on:click="release1">Laat los1</button>
              <button type="submit" v-on:click="clamp2">Pak2</button>
              <button type="submit" v-on:click="release2">Laat los2</button>
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
      <table>
        <tr>
          <td class="label">
            Naar vlak:
          </td>
          <td>
            <input v-model="vlak">
          </td>
          <td>
            <button type="submit" v-on:click="movevlak1">naar vlak arm1</button>
            <button type="submit" v-on:click="movevlak2">naar vlak arm2</button>
          </td>
        </tr>

        <tr>
          <td class="label">
            Manual pos:
          </td>
          <td>
            <input v-model="pos">
          </td>
          <td>
            <button type="submit" v-on:click="move">move</button>
          </td>
        </tr>

        <tr>
          <td class="label">
            A8:
          </td>
          <td>
            <input v-model="a8pos">
          </td>
          <td>
            <button type="submit" v-on:click="saveA8">Save</button>
          </td>
        </tr>

        <tr>
          <td class="label">
            H1:
          </td>
          <td>
            <input v-model="h1pos">
          </td>
          <td>
            <button type="submit" v-on:click="saveH1">Save</button>
          </td>
        </tr>

        <tr>
          <td class="label">
            A11:
          </td>
          <td>
            <input v-model="a11pos">
          </td>
          <td>
            <button type="submit" v-on:click="saveA11">Save</button>
          </td>
        </tr>

        <tr>
          <td class="label">
            H10:
          </td>
          <td>
            <input v-model="h10pos">
          </td>
          <td>
            <button type="submit" v-on:click="saveH10">Save</button>
          </td>
        </tr>

        <tr>
          <td class="label">
            A21:
          </td>
          <td>
            <input v-model="a21pos">
          </td>
          <td>
            <button type="submit" v-on:click="saveA21">Save</button>
          </td>
        </tr>

        <tr>
          <td class="label">
            H20:
          </td>
          <td>
            <input v-model="h20pos">
          </td>
          <td>
            <button type="submit" v-on:click="saveH20">Save</button>
          </td>
        </tr>

        <tr>
          <td class="label">
            Pakker hoogte:
          </td>
          <td>
            <input v-model="pakkerhoogte">
          </td>
          <td>
            <button type="submit" v-on:click="savePakkerhoogte">Save</button>
          </td>
        </tr>

        <tr>
          <td class="label">
            Snelheid:
          </td>
          <td>
            <input v-model="snelheid">
          </td>
          <td>
            <button type="submit" v-on:click="saveSnelheid">Save</button>
          </td>
        </tr>

        <tr>
          <td class="label">
            Delay na pak:
          </td>
          <td>
            <input v-model="delaynapak">
          </td>
          <td>
            <button type="submit" v-on:click="saveDelayNaPak">Save</button>
          </td>
        </tr>

        <tr>
          <td class="label">
            Delay na zet:
          </td>
          <td>
            <input v-model="delaynazet">
          </td>
          <td>
            <button type="submit" v-on:click="saveDelayNaZet">Save</button>
          </td>
        </tr>
      </table>
      <br>
      <br>
      <button type="submit" v-on:click="rebuildapp">rebuild application</button>
      <br>
      <br>
      <input v-model="fen_to_load">
      <button type="submit" v-on:click="loadFen">Load FEN</button>



    </div>
  </app-frame>
</template>
<script>
Vue.component("manual", {
  template: "#manual",
  data: () => ({
    manual: null,
    pos: "1000,1000",
    a8pos: "",
    a21pos: "",
    a11pos: "",
    h1pos: "",
    h10pos: "",
    h20pos: "",
    snelheid: "2.0",
    pakkerhoogte: "",
    delaynapak: "2.0",
    delaynazet: "2.0",
    vlak: "A8",
    fen_to_load: "r2qkb1r/1bpppppp/1pn2n2/p7/4PB2/2NP1Q2/PPP2PPP/R3KBNR w KQkq - 2 6"
  }),
  created() {
    this.load()
  },
  methods: {
    loadFen: function(){
      axios.post(`/api/game/loadfen`, this.fen_to_load)
          .then(res => {
          })
          .catch(error => {
            alert("Error")
          })

    },
    load: function (event) {
      fetch(`/api/a8`)
          .then(res => res.text())
          .then(text => this.a8pos = text)
          .catch(() => alert("Error"));

      fetch(`/api/a21`)
          .then(res => res.text())
          .then(text => this.a21pos = text)
          .catch(() => alert("Error"));

      fetch(`/api/a11`)
          .then(res => res.text())
          .then(text => this.a11pos = text)
          .catch(() => alert("Error"));

      fetch(`/api/h1`)
          .then(res => res.text())
          .then(text => this.h1pos = text)
          .catch(() => alert("Error"));

      fetch(`/api/h10`)
          .then(res => res.text())
          .then(text => this.h10pos = text)
          .catch(() => alert("Error"));

      fetch(`/api/h20`)
      fetch(`/api/h20`)
          .then(res => res.text())
          .then(text => this.h20pos = text)
          .catch(() => alert("Error"));

      fetch(`/api/snelheid`)
          .then(res => res.text())
          .then(text => this.snelheid = text)
          .catch(() => alert("Error"));

      fetch(`/api/pakkerhoogte`)
          .then(res => res.text())
          .then(text => this.pakkerhoogte = text)
          .catch(() => alert("Error"));

      fetch(`/api/delaynapak`)
          .then(res => res.text())
          .then(text => this.delaynapak = text)
          .catch(() => alert("Error"));

      fetch(`/api/delaynazet`)
          .then(res => res.text())
          .then(text => this.delaynazet = text)
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

    clamp1: function (event) {
      fetch(`/api/clamp1`)
          .catch(() => alert("Error"));
    },
    release1: function (event) {
      fetch(`/api/release1`)
          .catch(() => alert("Error"));
    },
    clamp2: function (event) {
      fetch(`/api/clamp2`)
          .catch(() => alert("Error"));
    },
    release2: function (event) {
      fetch(`/api/release2`)
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
    movevlak1: function (event) {
      axios.post(`/api/movevlak`, this.vlak + " 0")
          .then(res => {
          })
          .catch(error => {
            alert("Error")
          })
    },
    movevlak2: function (event) {
      axios.post(`/api/movevlak`, this.vlak + " 1")
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
    savePakkerhoogte: function (event) {
      axios.post(`/api/pakkerhoogte`, this.pakkerhoogte)
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
    rebuildapp: function (event) {
      fetch(`/api/rebuild`)
          .catch(() => alert("Error while rebuilding"));
    },
    close: function (event) {
      window.location.href = "/play";
    }
  }
});

</script>
<style scoped>
.myBorder {
  color: white;
  background-color: #4d6b85;
}
.label {
  color: white;
}

</style>
