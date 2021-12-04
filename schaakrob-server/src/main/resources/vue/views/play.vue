<template id="play">
  <app-frame>

    <div>

      <nav>
        <ul class="nav nav-pills pull-right">
          <li role="presentation"><a href="#" id="playPrev"><</a></li>
          <li role="presentation"><a href="#" id="playNext">></a></li>
        </ul>
      </nav>
      <nettoets-header activebutton="operationallog" title="Speelbord"></nettoets-header>
      <div class="myBorder">
        Game ({{ userdata.role }})
        <span v-if="userdata.role=='SPECTATOR'">
          <button type="submit" v-on:click="login">Login</button>
        </span>
        <span v-if="userdata.role!='SPECTATOR'">
          <button type="submit" v-on:click="logout">Logout</button>
      </span>


        <span>
        </span>
      </div>

      Aan zet: {{board.player}}
      <table width="100%">
        <tbody>
        <tr v-for="row in rows">
          <td v-for="col in cols" v-on:click="move(col.id+row.id)">

            <div v-bind:class="checkSquareClass(row.id, col.index)"  v-bind:id="col.id+row.id">
              <img v-if="boardrow[row.id - 1].boardcol[col.index]=='p'" src="/icons/png/stukken/not_selected/pion_wit.png" v-bind:id="col.id+row.id+'img'" class="stukdefault">
              <img v-if="boardrow[row.id - 1].boardcol[col.index]=='r'" src="/icons/png/stukken/not_selected/toren_wit.png" v-bind:id="col.id+row.id+'img'" class="stukdefault">
              <img v-if="boardrow[row.id - 1].boardcol[col.index]=='n'" src="/icons/png/stukken/selected/paard_wit.png" v-bind:id="col.id+row.id+'img'" class="stukdefault">
              <img v-if="boardrow[row.id - 1].boardcol[col.index]=='b'" src="/icons/png/stukken/not_selected/loper_wit.png" v-bind:id="col.id+row.id+'img'" class="stukdefault">
              <img v-if="boardrow[row.id - 1].boardcol[col.index]=='q'" src="/icons/png/stukken/not_selected/dame_wit.png" v-bind:id="col.id+row.id+'img'" class="stukdefault">
              <img v-if="boardrow[row.id - 1].boardcol[col.index]=='k'" src="/icons/png/stukken/not_selected/koning_wit.png" v-bind:id="col.id+row.id+'img'" class="stukdefault">


              <img v-if="boardrow[row.id - 1].boardcol[col.index]=='P'" src="/icons/png/stukken/not_selected/pion_zwart.png" v-bind:id="col.id+row.id+'img'" class="stukdefault">
              <img v-if="boardrow[row.id - 1].boardcol[col.index]=='R'" src="/icons/png/stukken/not_selected/toren_zwart.png" v-bind:id="col.id+row.id+'img'" class="stukdefault">
              <img v-if="boardrow[row.id - 1].boardcol[col.index]=='N'" src="/icons/png/stukken/selected/paard_zwart.png" v-bind:id="col.id+row.id+'img'" class="stukdefault">
              <img v-if="boardrow[row.id - 1].boardcol[col.index]=='B'" src="/icons/png/stukken/not_selected/loper_zwart.png" v-bind:id="col.id+row.id+'img'" class="stukdefault">
              <img v-if="boardrow[row.id - 1].boardcol[col.index]=='Q'" src="/icons/png/stukken/not_selected/dame_zwart.png" v-bind:id="col.id+row.id+'img'" class="stukdefault">
              <img v-if="boardrow[row.id - 1].boardcol[col.index]=='K'" src="/icons/png/stukken/not_selected/koning_zwart.png" v-bind:id="col.id+row.id+'img'" class="stukdefault">

              <img v-if="boardrow[row.id - 1].boardcol[col.index]==' '" src="/icons/png/stukken/empty.png" v-bind:id="col.id+row.id+'img'" class="stukdefault">

            </div>
          </td>
        </tr>
        </tbody>
      </table>


      <table>
        <tbody>
        <tr v-for="row in rows">
          <td v-for="col in cols">
            <button type="submit" class="schaakbtn" v-on:click="move(col.id+row.id)">
              {{ boardrow[row.id - 1].boardcol[col.index] }}
            </button>

          </td>
        </tr>
        </tbody>
      </table>
      <br>

      <span v-if="userdata.role!='SPECTATOR'">
              <button type="submit" v-on:click="toBase">Bord terugzetten</button>
          <span>
                        van: <input v-model="van">
                        naar: <input v-model="naar">
                  <button type="submit" v-on:click="again">Opnieuw</button>
           </span>
        <br>
          <!--
                <iframe width="640" height="480" src="https://rtsp.me/embed/8HkH3QT8/" frameborder="0" allowfullscreen></iframe>
          -->
        <br>
        <br>
        <hr>
       </span>
      <hr>
      <span v-if="userdata.role=='ADMIN'">
              <button type="submit" v-on:click="reset">Reset board</button>
              <button type="submit" v-on:click="sleep">Sleep</button>
              <button type="submit" v-on:click="home">Home</button>
              <button type="submit" v-on:click="computerMove">Computer zet</button>
              <button type="submit" v-on:click="loadfen">loadFen</button>
              <button type="submit" v-on:click="demo">demo</button>
              <button type="submit" v-on:click="manual">manual</button>
       </span>

      <br>
      <input v-model="board">

    </div>
  </app-frame>
</template>

<style>
.blacksquare{
  /*width: 120px;*/
  /*height: 120px;*/

  background-image: url("/icons/png/vlakken/zwart.png");
  object-fit: contain;
  background-size: contain;

  /*background-color: #ff6900;*/
}
.whitesquare{
  background-image: url("/icons/png/vlakken/wit.png");
  object-fit: contain;
  background-size: contain;
  /*width: 120px;*/
  /*height: 120px;*/
  /*background-color: #555555;*/
}
.blacksquare2{
  /*border:1px solid black;*/
  /*border:2px solid black;*/
  box-shadow: inset 0px 0px 10px rgba(255,255,255,1);
  /*box-shadow: 0 0 30px #719ECE;*/
  /*border:5px solid black;*/
  /*width: 120px;*/
  /*height: 120px;*/
  background-color: #ff6900;
}
.whitesquare2{
  box-shadow: inset 0px 0px 10px rgba(255,255,255,0.9);
  /*border:2px solid black;*/
  /*box-shadow: 0 0 10px #719ECE;*/
  /*outline: none !important;*/
  /*box-shadow: 0 0 5px rgba(81, 203, 238, 1);*/
  /*box-shadow: 0 0 5px #ffffff;*/
  /*width: 120px;*/
  /*height: 120px;*/
  background-color: #555555;
}
.stukhightlight{
  opacity: 1;
  /*background-color: white;*/
  /*display: inline-block;*/
}
.stukdefault{
  opacity: 1;
  /*background-color: white;*/
  /*display: inline-block;*/
}
</style>

<script>

Vue.component("play", {
  template: "#play",
  data: () => ({
    status: null,
    userdata: {
      username: "",
      role: ""
    },
    van: "",
    naar: "",
    testfield: {"s": "dd"},
    boardrow: [
      {boardcol: ["?", "?", "?", "?", "?", "?", "?", "?"]},
      {boardcol: ["?", "?", "?", "?", "?", "?", "?", "?"]},
      {boardcol: ["?", "?", "?", "?", "?", "?", "?", "?"]},
      {boardcol: ["?", "?", "?", "?", "?", "?", "?", "?"]},
      {boardcol: ["?", "?", "?", "?", "?", "?", "?", "?"]},
      {boardcol: ["?", "?", "?", "?", "?", "?", "?", "?"]},
      {boardcol: ["?", "?", "?", "?", "?", "?", "?", "p"]},
      {boardcol: ["?", "?", "?", "?", "?", "?", "?", "t"]},
    ],
    board: {
      player: "?"
    },
    rows: [
      {id: 8},
      {id: 7},
      {id: 6},
      {id: 5},
      {id: 4},
      {id: 3},
      {id: 2},
      {id: 1},
    ],

    cols: [
      {id: 'A', index: 0},
      {id: 'B', index: 1},
      {id: 'C', index: 2},
      {id: 'D', index: 3},
      {id: 'E', index: 4},
      {id: 'F', index: 5},
      {id: 'G', index: 6},
      {id: 'H', index: 7},
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
      fetch(`/api/game/load`)
          .then(res => res.text())
          .then(text => this.updateBoard(JSON.parse(text)))
          .catch(err => alert(err));
      fetch(`/api/userdata`)
          .then(res => res.text())
          .then(text => this.userdata = JSON.parse(text))
          .catch(() => alert("Error"));
    },
    reset: function (event) {
      fetch(`/api/game/reset`)
          .then(res => res.text())
          .then(text => this.updateBoard(JSON.parse(text)))
          .catch(() => alert("Error"));
    },
    sleep: function (event) {
      fetch(`/api/game/sleep`)
          .then(res => res.text())
          .catch(() => alert("Error"));
    },
    home: function (event) {
      fetch(`/api/game/home`)
          .then(res => res.text())
          .catch(() => alert("Error"));
    },
    toBase: function (event) {
      fetch(`/api/game/restoreboard`)
          .then(res => res.text())
          .catch(() => alert("Error"));
    },
    loadfen: function (event) {
      fetch(`/api/game/loadfen`)
          .then(res => res.text())
          .catch(() => alert("Error"));
    },
    computerMove: function (event) {
      fetch(`/api/game/computermove`)
          .then(res => res.text())
          .then(text => this.updateBoard(JSON.parse(text)))
          .catch(() => alert("Error"));
    },
    move: function (vlak) {

      $( "#B2" ).addClass('blacksquare2')
      $( "#C2" ).addClass('whitesquare2')
      $( "#B3" ).addClass('blacksquare2')
      $( "#C3" ).addClass('whitesquare2')

      $("#"+vlak+"img").attr("src",'/empty.png');

      $("#E3img").addClass('stukhightlight').removeClass('stukdefault')

      if (this.userdata.role=='SPECTATOR') return


      if (this.van == "") {
        this.van = vlak
      } else {
        if (this.naar == "") {
          this.naar = vlak
          fetch(`/api/game/ownmove/` + this.van + '/' + this.naar)
              .then(res => res.text())
              .then(text => this.updateBoard(JSON.parse(text)))
              .then(text => this.computerMove())
              .catch(() => alert("Error"));
          this.naar = ""
          this.van = ""


        } else {
          this.van = vlak
          this.naar = ""
        }
      }
    },
    logout: function (event) {
      fetch(`/api/logout`)
          .catch(() => alert("Error"))
          .then(text => window.location.href = "/play");
    },
    login: function (event) {
      window.location.href = "/login";
    },
    demo: function (event) {
      window.location.href = "/demo";
    },
    manual: function (event) {
      window.location.href = "/manual";
    },
    again: function () {
      this.naar = ""
      this.van = ""
    },
    updateBoard: function (newBoard) {
      console.info(newBoard)
      this.board = newBoard


      console.log(this.board);
      rows2 = this.rows
      // console.log("t2");
      squares = this.board.squares
      // console.log("t3");
      boardrow = this.boardrow
      // console.log("t4");

      this.cols.forEach(function (col, index) {
        // console.log("t5");
        rows2.forEach(function (row, index2) {
          sq = col.id + "" + row.id
          // console.log("t6");
          squares.forEach(function (item, index3) {
            if (item.field == sq) {
              boardrow[7 - index2].boardcol[index] = item.piece
            }
          });
        });
      });


      // this.board.squares.forEach(function (item, index) {
      // console.log(item, index);
      // });
      // this.boardrow = boardrow
      // this.boardrow[1].boardcol[1] = "QQ"
      this.testfield = "jj"

    },
    checkSquareClass: function (row,col){
      if ((row+col)%2==1) return "blacksquare"
      if ((row+col)%2==0) return "whitesquare"

    },
    getPieceImage: function (row,col){
      if ((row+col)%2==1) return "blacksquare"
      if ((row+col)%2==0) return "whitesquare"

    }

  }
});

$(document).ready(function () {
  $("#playPrev").click(function () {
    window.location.href = "/manual";
  });
  $("#playNext").click(function () {
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
  width: 30px;
  height: 30px;
  min-width: 0px;
}
</style>
