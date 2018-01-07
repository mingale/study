/**
 * 
 */
var typeNum = 0;

function spaceType(type) {
	typeNum = type;
}


function spaceDivChange() {
	var x = document.getElementById('widthX').value;
	var y = document.getElementById('heightY').value;
	
	var spaceDiv = document.getElementById('spaceDiv');
	
	var space = '';
	for(var i = 0; i < x; i += 1) {
		for(var j = 0; j < y; j += 1) {
			space += '<div class="div">(' 
					+ '<button>'
					+ i + ',' + j 
					+ '</button>' 
					+ ')</div>';
		}
		space += '<br>';
	}
	spaceDiv.innerHTML = space;
}