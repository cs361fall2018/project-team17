var isSetup = true;
var sonarClicked=false;
var sonarUsed=0;
var moveFleet=0;
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
    else if (attack.result === "SUNK") {
        className = "hit";
        if(elementId === "opponent" && moveFleet === 2){
            document.getElementById("move-fleet").classList.remove('hide');
            moveFleet++;
        }else if(elementId === "opponent" && moveFleet < 3){
            moveFleet++;
        }
        if(elementId === "opponent" && sonarUsed == 0) {
            document.getElementById("place_sonar").classList.remove('hide');
        }
    }else if( attack.result === "CAPTAIN"){
        className = "captain";
    }
    else if (attack.result === "SURRENDER") {
        className = "hit";
        displayEndGame(surrenderText);
    }
    document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
});
}

function markSonar(board, elementId) {
    board.sonar.forEach((sonar) => {
        let className;
    if (sonar.result === "VISIBLE")
        className = "sonar_ship";
    else if (sonar.result === "HIDDEN")
        className = "sonar_water";
    var cell = document.getElementById(elementId).rows[sonar.location.row-1].cells[sonar.location.column.charCodeAt(0) - 'A'.charCodeAt(0)];

    if(!cell.classList.contains("hit") && !cell.classList.contains("miss") && !cell.classList.contains("captain")) {
        if(sonar.center === true) {
            let center = document.createElement('div');
            center.classList.add('sonar_center');
            cell.appendChild(center);
        }
        cell.classList.add(className);

    }
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

    //add in to see the opponents ships, for easy debug
    game.opponentsBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        document.getElementById("opponent").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }));

    game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }));
    markHits(game.opponentsBoard, "opponent", "You won the game");
    markHits(game.opponentsBoard, "opponent", "You won the game");
    markHits(game.playersBoard, "player", "You lost the game");
    markSonar(game.opponentsBoard, "opponent");
}

var oldListener;
function registerCellListener(f, board) {
    let el = document.getElementById(board);
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
        if(shipType==undefined) {
            document.getElementById("error-menu").classList.remove("hide");
            document.getElementById("error-menu").innerHTML = "*You have not selected a ship. Please select one using the buttons below";
            return;
        }
        sendXhr("POST", "/place", {game: game, shipType: shipType, x: row, y: col, isVertical: vertical}, function(data) {
            game = data;
            disableShipButton(shipType);
            redrawGrid();
            placedShips++;
            shipType = undefined;
            if (placedShips == 3) {
                prepareAttackPhase();
                isSetup = false;
                registerCellListener((e) => {}, "player");
            }
        });

    } else {
        if(!sonarClicked) {
            if (document.getElementById("opponent").rows[this.parentNode.rowIndex].cells[this.cellIndex].classList.contains("hit") || document.getElementById("opponent").rows[this.parentNode.rowIndex].cells[this.cellIndex].classList.contains("miss")) {
                document.getElementById("error-menu").classList.remove("hide");
                document.getElementById("error-menu").innerHTML = "*You have already selected that space. Please select a different one";
                return;
            }
            sendXhr("POST", "/attack", {game: game, x: row, y: col}, function (data) {
                game = data;
                redrawGrid();
            });
        }
        else if (sonarClicked) {
            sendXhr("POST", "/sonar", {game: game, x:row, y:col}, function(data) {
                game = data;
                redrawGrid();
                sonarUsed++;
                if(sonarUsed == 2) {
                    document.getElementById("place_sonar").classList.add('hide');
                }
            });
            sonarClicked=false;
            document.getElementById("place_sonar").classList.remove("clicked");
        }
    }

}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    console.log("Status1: ", req.status);
    req.addEventListener("load", function(event) {
        console.log("Status: ", req.status);
        if (req.status != 200) {
            if (isSetup) {
                document.getElementById("error-menu").classList.remove("hide");
                document.getElementById("error-menu").innerHTML = "*You are trying to place a ship on another or it is out of bounds. Please place your ship on empty spaces";
            } else {
                alert("Cannot complete the action");
            }
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
    registerCellListener(place(size), "player");
}

function checkSonarHover(cell) {
    if(cell!==undefined) {cell.classList.toggle("placed");}
}

function placeSonar() {
    return function() {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        let table = document.getElementById("opponent");
        let tableRow;

        //Cross
        for (let i=0; i<3; i++) {

            tableRow = table.rows[row+i]; //Top side
            if (tableRow !== undefined) {checkSonarHover(tableRow.cells[col]);}
            tableRow = table.rows[row-i]; //Bottom side
            if (tableRow !== undefined) {checkSonarHover(tableRow.cells[col]);}

            checkSonarHover(table.rows[row].cells[col+i]); //Right side
            checkSonarHover(table.rows[row].cells[col-i]); //Left side
        }

        //Bottom part of square corners
        tableRow = table.rows[row+1];
        if(tableRow !== undefined) {
            checkSonarHover(tableRow.cells[col+1]); //bottom right corner
            checkSonarHover(tableRow.cells[col-1]); //bottom left corner
        }
        //Top part of square corners
        tableRow = table.rows[row-1];
        if(tableRow !== undefined) {
            checkSonarHover(tableRow.cells[col-1]); //top left corner
            checkSonarHover(tableRow.cells[col+1]); //top right corner
        }
    }
}

function move(direction){
    document.getElementById("move-fleet-" + direction).classList.add("clicked");
    sendXhr("POST", "/moveFleet", {Game: game, direction: direction}, function(data){
       game = data;
       redrawGrid();
       document.getElementById("move-fleet-" + direction).classList.remove("clicked");
       moveFleet++;
       if(moveFleet === 5){
           document.getElementById("move-fleet").classList.add("hide");
       }
    });
}

function initGame() {
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);

    document.getElementById("place_minesweeper").addEventListener("click", function(e){ placeShipButton("MINESWEEPER", 2)});
    document.getElementById("place_destroyer").addEventListener("click", function(e) { placeShipButton("DESTROYER", 3)});
    document.getElementById("place_battleship").addEventListener("click", function(e) { placeShipButton("BATTLESHIP", 4)});

    document.getElementById("move-fleet-N").addEventListener("click", function(e) {
        // document.getElementById("move-fleet-N").classList.add("clicked");
        move("N");
    });
    document.getElementById("move-fleet-E").addEventListener("click", function(e) {
        // document.getElementById("move-fleet-E").classList.add("clicked");
        move("E");
    });
    document.getElementById("move-fleet-S").addEventListener("click", function(e) {
        // document.getElementById("move-fleet-S").classList.add("clicked");
        move("S");
    });
    document.getElementById("move-fleet-W").addEventListener("click", function(e) {
        // document.getElementById("move-fleet-W").classList.add("clicked");
        move("W");
    });

    document.getElementById("place_sonar").addEventListener("click", function(e) { sonarClicked=true; document.getElementById("place_sonar").classList.add("clicked"); registerCellListener(placeSonar(), "opponent");});

    document.getElementById("start-button").addEventListener("click", function(){
       document.getElementById("place-menu-container").classList.remove("hide");
       document.getElementById("start-button").setAttribute("class", "hide")
    });
    sendXhr("GET", "/game", {}, function(data) {
        game = data;
    });
}
