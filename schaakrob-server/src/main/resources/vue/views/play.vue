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

      <table>
        <tbody>
        <tr v-for="row in rows">
          <td v-for="col in cols">
            <button type="submit" class="schaakbtn" v-on:click="move(col.id+row.id)">{{boardrow[row.id-1] .boardcol[col.index]}}</button>

          </td>
        </tr>
        </tbody>
      </table>
      <br>
      <hr>
      <br>
      <span>
              <button type="submit" v-on:click="reset">Reset board</button>
              <button type="submit" v-on:click="computerMove">Computer zet</button>
              <button type="submit" v-on:click="again">Opnieuw</button>
                    van: <input v-model="van">
                    naar: <input v-model="naar">
       </span>
      <br>
        <input v-model="board">

    </div>
  </app-frame>
</template>
<script>


Vue.component("play", {
  template: "#play",
  data: () => ({
    status: null,
    van: "",
    naar: "",
    testfield: {"s":"dd"},
    boardrow: [
      {boardcol: ["?","?","?","?","?","?","?","?"]},
      {boardcol: ["?","?","?","?","?","?","?","?"]},
      {boardcol: ["?","?","?","?","?","?","?","?"]},
      {boardcol: ["?","?","?","?","?","?","?","?"]},
      {boardcol: ["?","?","?","?","?","?","?","?"]},
      {boardcol: ["?","?","?","?","?","?","?","?"]},
      {boardcol: ["?","?","?","?","?","?","?","p"]},
      {boardcol: ["?","?","?","?","?","?","?","t"]},
        ],
    board: {    },
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
      {id: 'A', index:0},
      {id: 'B', index:1},
      {id: 'C', index:2},
      {id: 'D', index:3},
      {id: 'E', index:4},
      {id: 'F', index:5},
      {id: 'G', index:6},
      {id: 'H', index:7},
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
          .then(res => res.text())
          .then(text => this.updateBoard(JSON.parse(text)))
          .catch(() => alert("Error"));
    },
    computerMove: function (event) {
      fetch(`/api/game/computermove`)
          .then(res => res.text())
          .then(text => this.updateBoard(JSON.parse(text)))
          .catch(() => alert("Error"));
    },
    move: function (vlak) {
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
    again: function () {
      this.naar = ""
      this.van = ""
    },
    updateBoard: function (newBoard) {

      this.board = newBoard


      console.log(this.board);
      rows2 = this.rows
      // console.log("t2");
      squares =  this.board.squares
      // console.log("t3");
      boardrow = this.boardrow
      // console.log("t4");

      this.cols.forEach(function (col, index) {
        // console.log("t5");
        rows2.forEach(function (row, index2) {
          sq = col.id+""+row.id
          // console.log("t6");
           squares.forEach(function (item, index3) {
             if (item.field==sq) {
               boardrow[7-index2].boardcol[index] = item.piece
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

    }
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
