/**
 * on clicking the type the following will be generated
 * 
 * type 8 = Percentage + Skus
 */
var ls2elems = ["type=label|for=qty|value=Enter the quantity" , 
				"type=input|id=qty|value=|name=qty",
				"type=label|for=price|value=Enter the save price" , 
				"type=input|id=price|value=|name=price",
				"type=label|for=stores|value=Enter Store Seperated by commas",
				"type=textarea|id=stores|value=|name=stores",
				"type=label|for=skus|value=Enter skus Seperated by commas",
				"type=textarea|id=skus|value=|name=skus"
				];

var ls8elems = [ "type=label|for=percent|value=Enter the percentage" , 
				"type=input|id=percent|value=|name=price",
				"type=label|for=stores|value=Enter Store Seperated by commas",
				"type=textarea|id=stores|value=|name=stores",
				"type=label|for=skus|value=Enter skus Seperated by commas",
				"type=textarea|id=skuss|value=|name=skus"
              ];

var ls15elems = ["type=label|for=qty|value=Enter the quantity" , 
				"type=input|id=qty|value|name=qty",
				"type=label|for=price|value=Enter the buy price" , 
				"type=input|id=price|value|name=price",
				"type=label|for=stores|value=Enter Store Seperated by commas",
				"type=textarea|id=stores|value=|name=stores",
				"type=label|for=skus|value=Enter skus Seperated by commas",
				"type=textarea|id=skuss|value=|name=skus"
				];

var ls11elems = ["type=label|for=qty|value=Enter the quantity" , 
				"type=input|id=qty|value|name=qty",
				"type=label|for=priceA|value=Enter the price A" , 
				"type=input|id=priceA|value=|name=priceA",
				"type=label|for=priceB|value=Enter the price B" , 
				"type=input|id=priceB|value=|name=priceB",
				"type=label|for=price|value=Enter the Selling price" , 
				"type=input|id=price|value|name=price",
				"type=label|for=stores|value=Enter Store Seperated by commas",
				"type=textarea|id=stores|value=|name=stores",
				"type=label|for=skus|value=Enter skus Seperated by commas for Group 1",
				"type=textarea|id=skuss|value=|name=skus",
				"type=label|for=skus2|value=Enter skus Seperated by commas for Group2",
				"type=textarea|id=skuss|value=|name=skus2"
				];


function GetDisplayText() {
	var lstype = document.getElementById("lsType").value;
	DisplayLSFields(lstype);
}

function DisplayLSFields(lstype){
	var container = document.getElementById("container");
	if(container != null){
		container.remove();
	}
	if (lstype == "None")
		return;
	
	var lsArr = null;
	if(lstype == "TYPE8"){
		lsArr = ls8elems;
	}else if(lstype == "TYPE11"){
		lsArr = ls11elems;
	}else if(lstype == "TYPE15"){
		lsArr = ls15elems;
	}else if(lstype == "TYPE2"){
		lsArr = ls2elems;
	}
	
	var newdiv = document.createElement('div');
	newdiv.id = "container";
	document.getElementById("display").appendChild(newdiv);
	
	var type = "";
	var id = "";
	var value = "";
	var name = "";
	
	var elem = null;
	for(var i=0;i<lsArr.length;i++){
		type = getType(lsArr[i], 'type');
		id = getType(lsArr[i], 'id');
		value = getType(lsArr[i], 'value');
		name = getType(lsArr[i],'name');
		
		elem = document.createElement(type);
		if(type == 'input'){
			elem.setAttribute("id",id);
			elem.setAttribute("name",name);
		}else if(type == 'label'){
			elem.setAttribute("for",id);
			elem.innerHTML = value; 
		}else if(type == 'textarea'){
			elem.setAttribute("name",name);
			elem.setAttribute("id",id);
		}
		document.getElementById("container").appendChild(elem);
		document.getElementById("container").appendChild(document.createElement("br"));
	}
}

function getType(data, fieldName){
	var fields = data.split(/\|/);

	for ( var j = 0; j < fields.length; j++) {
		var splitStr = fields[j].split("=");
		
		if (splitStr[0] == fieldName) {
			if (splitStr.length > 1) {
				return splitStr[1];
			} else {
				return null;
			}
		}
	}

	return null;
}

function Init() {
	var ele = document.getElementById("EnvInfo").value;
	document.getElementById("env").innerHTML = ele;
}

function validate(){
	var lsType = document.getElementById("lsType").value;
	var env = document.getElementById("EnvInfo").value;
	if(lsType==null || lsType=="" || lsType=='None' || env==null || env=='' || env=='None')
		return false;
	switch(lsType){
		case 'TYPE2':
			var qty = Number(document.getElementById("qty").value);
			var price = Number(document.getElementById("price").value);
			if(typeof(qty)!=Number || typeofqty(price)!=Number){
				alert("Quantity or price must be number");
				return false;
			}
			if(qty==null || qty=='' || price==null || price=='')
				return false;
		break;
		case 'TYPE8':
			var percent = Number(document.getElementById("percent").value);
			if(typeof(percent)!=Number){
				alert("Percent must be number");
				return false;
			}
			if(percent==null || percent=='')
				return false;
		break;
		case 'TYPE11':
			var qty = Number(document.getElementById("qty").value);
			var price = Number(document.getElementById("price").value);
			var priceA = Number(document.getElementById("priceA").value); 
			var priceB = Number(document.getElementById("priceB").value);
		break;
		case 'TYPE15':
			var qty = Number(document.getElementById("qty").value);
			var price = Number(document.getElementById("price").value);
			if(qty==null || qty=='' || price==null || price=='')
				return false;
		break;
	}
	return true;
}
				