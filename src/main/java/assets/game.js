var isSetup = true;
var placedShips = 0;
var game;
var shipType;
var vertical;

function makeGrid(table, isPlayer) {
    var header=true; var sides=true; var skip=true;
    var c = 'A';
    for (i=0; i<11; i++) {
        let row = document.createElement('tr');
        for (j=0; j<11; j++) {
            if(header || sides) {
                let columnHeader = document.createElement('th');
                if(!skip) {
                    if(sides) { columnHeader.innerHTML = (c.charCodeAt(0)-74).toString(); }
                    else if (header) { columnHeader.innerHTML = c; }

                    c = String.fromCharCode(c.charCodeAt(0) + 1);
                }
                skip=false;
                row.appendChild(columnHeader);
            }
            else {
                let column = document.createElement('td');
                column.addEventListener("click", cellClick);
                row.appendChild(column);
            }
            sides=false;
        }
        sides=true;
        header=false;
        table.appendChild(row);
    }
}

function displayEndGame(endGame) {
    if(endGame.indexOf('won') > -1) {
        document.getElementById('win').classList.remove('hide');
    } else if(endGame.indexOf('lost') > -1) {
        document.getElementById('lose').classList.remove('hide');
    }

    document.getElementById("states").classList.remove('hide');
    document.getElementById("board-container").classList.add('fadeout');
    document.getElementById("attack-menu").classList.add('hide');
    setTimeout(function() {
        document.getElementById("player").classList.add('hide');
        document.getElementById("opponent").classList.add('hide');
    }, (2000));
}

function markHits(board, elementId, surrenderText) {
    board.attacks.forEach((attack) => {
        let className;
    if (attack.result === "MISS")
        className = "miss";
    else if (attack.result === "HIT")
        className = "hit";
    else if (attack.result === "SUNK")
        className = "hit";
    else if (attack.result === "SURRENDER") {
        className = "hit";
        displayEndGame(surrenderText);
    }
    document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
});
}

function redrawGrid() {
    Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    if (game === undefined) {
        return;
    }

    game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }));
    markHits(game.opponentsBoard, "opponent", "You won the game");
    markHits(game.playersBoard, "player", "You lost the game");
}

var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");
    for (i=1; i<11; i++) {
        for (j=1; j<11; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldListener = f;
}

function disableShipButton(shipType){
    if(shipType == "MINESWEEPER"){
        document.getElementById("place_minesweeper").setAttribute("disabled", "disabled");
        document.getElementById("place_minesweeper").setAttribute("class", "disabled");
    }else if(shipType == "DESTROYER"){
        document.getElementById("place_destroyer").setAttribute("disabled", "disabled");
        document.getElementById("place_destroyer").setAttribute("class", "disabled");
    }else if(shipType == "BATTLESHIP"){
        document.getElementById("place_battleship").setAttribute("disabled", "disabled");
        document.getElementById("place_battleship").setAttribute("class", "disabled");
    }
}

function prepareAttackPhase() {
    document.getElementById("place-menu").setAttribute("class", "hide");
    document.getElementsByClassName("checkbox-container")[0].setAttribute("class", "hide");
    document.getElementById("attack-menu").classList.remove("hide");
    document.getElementById("opponent").classList.remove("hide");
    document.getElementById("player").classList.add("shrink");
    document.getElementById("flex-player").classList.add("media-player");
}

function cellClick() {

    if(!(document.getElementById("error-menu").classList.contains("hide"))){
        document.getElementById("error-menu").classList.add("hide");
    }

    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);
    if(isSetup) {
        // if(vertical){
        //     if(shipType == "MINESWEEPER"){
        //         if(this.parentNode.rowIndex > 9){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship outside of the board. Please make sure each of your ships spaces are on the board";
        //             return;
        //         }
        //         if(this.classList.contains("occupied")){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //             return;
        //         } else if (document.getElementById("player").rows[this.parentNode.rowIndex + 1].cells[this.cellIndex].classList.contains("occupied")){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //             return;
        //         }
        //
        //     }
        //     else if(shipType == "DESTROYER"){
        //         if(this.parentNode.rowIndex > 8){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship outside of the board. Please make sure each of your ships spaces are on the board";
        //             return;
        //         }
        //         if(this.classList.contains("occupied")){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //             return;
        //         } else if (document.getElementById("player").rows[this.parentNode.rowIndex + 1].cells[this.cellIndex].classList.contains("occupied")){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //             return;
        //         } else if (document.getElementById("player").rows[this.parentNode.rowIndex + 2].cells[this.cellIndex].classList.contains("occupied")){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //             return;
        //         }
        //     }
        //     else if(shipType == "BATTLESHIP"){
        //         if(this.parentNode.rowIndex > 7){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship outside of the board. Please make sure each of your ships spaces are on the board";
        //             return;
        //         }
        //             if(this.classList.contains("occupied")){
        //                 document.getElementById("error-menu").classList.remove("hide");
        //                 document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //                 return;
        //             } else if (document.getElementById("player").rows[this.parentNode.rowIndex + 1].cells[this.cellIndex].classList.contains("occupied")){
        //                 document.getElementById("error-menu").classList.remove("hide");
        //                 document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //                 return;
        //             } else if (document.getElementById("player").rows[this.parentNode.rowIndex + 2].cells[this.cellIndex].classList.contains("occupied")){
        //                 document.getElementById("error-menu").classList.remove("hide");
        //                 document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //                 return;
        //             } else if (document.getElementById("player").rows[this.parentNode.rowIndex + 3].cells[this.cellIndex].classList.contains("occupied")){
        //               document.getElementById("error-menu").classList.remove("hide");
        //               document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //               return;
        //             }
        //     }
        //     else{
        //         document.getElementById("error-menu").classList.remove("hide");
        //         document.getElementById("error-menu").innerHTML = "*You have not selected a ship. Please select one using the buttons below";
        //         return;
        //     }
        // } else {
        //     if(shipType == "MINESWEEPER"){
        //         if(this.cellIndex > 9){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship outside of the board. Please make sure each of your ships spaces are on the board";
        //             return;
        //         }
        //         if(this.classList.contains("occupied")){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //             return;
        //         } else if (this.parentNode.cells[this.cellIndex + 1].classList.contains("occupied")){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //             return;
        //         }
        //     }
        //     else if(shipType == "DESTROYER"){
        //         if(this.cellIndex > 8){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship outside of the board. Please make sure each of your ships spaces are on the board";
        //             return;
        //         }
        //         if(this.classList.contains("occupied")){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //             return;
        //         } else if (this.parentNode.cells[this.cellIndex + 1].classList.contains("occupied")){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //             return;
        //         } else if (this.parentNode.cells[this.cellIndex + 2].classList.contains("occupied")){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //             return;
        //         }
        //     }
        //     else if(shipType == "BATTLESHIP"){
        //         if(this.cellIndex > 7){
        //             document.getElementById("error-menu").classList.remove("hide");
        //             document.getElementById("error-menu").innerHTML = "*You are trying to place a ship outside of the board. Please make sure each of your ships spaces are on the board";
        //             return;
        //         }
        //             if(this.classList.contains("occupied")){
        //                 document.getElementById("error-menu").classList.remove("hide");
        //                 document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //                 return;
        //             } else if (this.parentNode.cells[this.cellIndex + 1].classList.contains("occupied")){
        //                 document.getElementById("error-menu").classList.remove("hide");
        //                 document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //                 return;
        //             } else if (this.parentNode.cells[this.cellIndex + 2].classList.contains("occupied")){
        //                 document.getElementById("error-menu").classList.remove("hide");
        //                 document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //                 return;
        //             } else if (this.parentNode.cells[this.cellIndex + 3].classList.contains("occupied")){
        //                 document.getElementById("error-menu").classList.remove("hide");
        //                 document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
        //                 return;
        //             }
        //     }
        //     else{
        //         document.getElementById("error-menu").classList.remove("hide");
        //         document.getElementById("error-menu").innerHTML = "*You have not selected a ship. Please select one using the buttons below";
        //         return;
        //     }
        // }
        if(shipType==undefined) {
            document.getElementById("error-menu").classList.remove("hide");
            document.getElementById("error-menu").innerHTML = "*You have not selected a ship. Please select one using the buttons below";
            return;
        }
        console.log(shipType);
        sendXhr("POST", "/place", {game: game, shipType: shipType, x: row, y: col, isVertical: vertical}, function(data) {
            game = data;
            disableShipButton(shipType);
            redrawGrid();
            placedShips++;
            shipType = undefined;
            if (placedShips == 3) {
                prepareAttackPhase();
                isSetup = false;
                registerCellListener((e) => {});
            }
        });

    } else {
        if(document.getElementById("opponent").rows[this.parentNode.rowIndex].cells[this.cellIndex].classList.contains("hit") || document.getElementById("opponent").rows[this.parentNode.rowIndex].cells[this.cellIndex].classList.contains("miss")){
            document.getElementById("error-menu").classList.remove("hide");
            document.getElementById("error-menu").innerHTML = "*You have already selected that space. Please select a different one";
            return;
        }
        sendXhr("POST", "/attack", {game: game, x: row, y: col}, function(data) {
            game = data;
            redrawGrid();
        })
    }

}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        if (req.status != 200) {
            if (isSetup) {
                document.getElementById("error-menu").classList.remove("hide");
                document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on an occupied space. Please place your ship on empty spaces";
            } else
                alert("Cannot complete the action");
            return;
        }
        handler(JSON.parse(req.responseText));
    });
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(data));
}

function place(size) {
    return function() {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        vertical = document.getElementById("is_vertical").checked;
        let table = document.getElementById("player");
        for (let i=0; i<size; i++) {
            let cell;
            if(vertical) {
                let tableRow = table.rows[row+i];
                if (tableRow === undefined) {
                    // ship is over the edge; let the back end deal with it
                    break;
                }
                cell = tableRow.cells[col];
            } else {
                cell = table.rows[row].cells[col+i];
            }
            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                break;
            }
            cell.classList.toggle("placed");
        }
    }
}

function placeShipButton(shipName, size) {
    shipType = shipName;
    var id = "place_" + shipName.toLowerCase();
    for(var i = 0; i < document.getElementsByClassName('clicked').length; i++){
        document.getElementsByClassName("clicked")[i].classList.remove("clicked");
    }
    document.getElementById(id).setAttribute("class", "clicked");
    registerCellListener(place(size));
}

function initGame() {
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);

    document.getElementById("place_minesweeper").addEventListener("click", function(e){ placeShipButton("MINESWEEPER", 2)});
    document.getElementById("place_destroyer").addEventListener("click", function(e) { placeShipButton("DESTROYER", 3)});
    document.getElementById("place_battleship").addEventListener("click", function(e) { placeShipButton("BATTLESHIP", 4)});

    document.getElementById("start-button").addEventListener("click", function(){
       document.getElementById("place-menu-container").classList.remove("hide");
       document.getElementById("start-button").setAttribute("class", "hide")
    });
    sendXhr("GET", "/game", {}, function(data) {
        game = data;
    });
}
