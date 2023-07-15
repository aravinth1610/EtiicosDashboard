
if(window.location.pathname == "/etiicos/Approved")
{
	
	
$("#approvalDwnBtn").on('click',function(){

$.get("innerprocess/etiicos/approvaldownloard",function(approvalExcelDownloadResult){
	
	if(approvalExcelDownloadResult.status == 200 && approvalExcelDownloadResult.message == 'success')
	{

//ReFormate Database Date --> Start 
function getDate(formateDate) {
    var d = (new Date(formateDate) + '').split(' ');
    d[2] = d[2] + ',';
    return [d[0], d[1], d[2], d[3]].join(' ');
}

var formateDate = Date.parse(new Date());
var today = getDate(formateDate);

var date=today.slice(8,10);
var month=today.slice(4,7);
var year=today.slice(12,16);
var reFormatedDate = date+" "+month+" "+year;
		
	var approvalDetails = approvalExcelDownloadResult.content;
	
	
	// Create a new workbook
const workbook = XLSX.utils.book_new();

// Convert the array to a worksheet
const worksheet = XLSX.utils.aoa_to_sheet(approvalDetails);

// Add the worksheet to the workbook
XLSX.utils.book_append_sheet(workbook, worksheet, 'My Sheet');

// Write the workbook to a file
XLSX.writeFile(workbook, 'OPDApproval_'+reFormatedDate+'.xlsx');
	
	}else{}
	
})
})
}

if(window.location.pathname == "/etiicos/Reject")
{
$("#rejectDwnBtn").on('click',function(){

$.get("innerprocess/etiicos/rejectdownloard",function(rejectExcelDownloadResult){
	
	if(rejectExcelDownloadResult.status == 200 && rejectExcelDownloadResult.message == 'success')
	{

//ReFormate Database Date --> Start 
function getDate(formateDate) {
    var d = (new Date(formateDate) + '').split(' ');
    d[2] = d[2] + ',';
    return [d[0], d[1], d[2], d[3]].join(' ');
}

var formateDate = Date.parse(new Date());
var today = getDate(formateDate);

var date=today.slice(8,10);
var month=today.slice(4,7);
var year=today.slice(12,16);
var reFormatedDate = date+" "+month+" "+year;
		
	var rejectDetails = rejectExcelDownloadResult.content;
	
	// Create a new workbook
const workbook = XLSX.utils.book_new();

// Convert the array to a worksheet
const worksheet = XLSX.utils.aoa_to_sheet(rejectDetails);

// Add the worksheet to the workbook
XLSX.utils.book_append_sheet(workbook, worksheet, 'My Sheet');

// Write the workbook to a file
XLSX.writeFile(workbook, 'OPDReject_'+reFormatedDate+'.xlsx');
	
	}else{}
	
})
})
}

if(window.location.pathname == "/etiicos/Token-Approved")
{
$("#tokenapprovalDwnBtn").on('click',function(){

$.get("innerprocess/etiicos/tokenapprovaldownloard",function(tokenApprovalExcelDownloadResult){
	
	if(tokenApprovalExcelDownloadResult.status == 200 && tokenApprovalExcelDownloadResult.message == 'success')
	{

//ReFormate Database Date --> Start 
function getDate(formateDate) {
    var d = (new Date(formateDate) + '').split(' ');
    d[2] = d[2] + ',';
    return [d[0], d[1], d[2], d[3]].join(' ');
}

var formateDate = Date.parse(new Date());
var today = getDate(formateDate);

var date=today.slice(8,10);
var month=today.slice(4,7);
var year=today.slice(12,16);
var reFormatedDate = date+" "+month+" "+year;
		
	var tokenApprovalDetails = tokenApprovalExcelDownloadResult.content;
	
	// Create a new workbook
const workbook = XLSX.utils.book_new();

// Convert the array to a worksheet
const worksheet = XLSX.utils.aoa_to_sheet(tokenApprovalDetails);

// Add the worksheet to the workbook
XLSX.utils.book_append_sheet(workbook, worksheet, 'My Sheet');

// Write the workbook to a file
XLSX.writeFile(workbook, 'TokenApproval_'+reFormatedDate+'.xlsx');
	
	}else{}
	
})
})
}

if(window.location.pathname == "/etiicos/Token-Reject")
{
$("#tokenrejectDwnBtn").on('click',function(){

$.get("innerprocess/etiicos/tokenrejectdownloard",function(tokenRejectExcelDownloadResult){
	
	if(tokenRejectExcelDownloadResult.status == 200 && tokenRejectExcelDownloadResult.message == 'success')
	{

//ReFormate Database Date --> Start 
function getDate(formateDate) {
    var d = (new Date(formateDate) + '').split(' ');
    d[2] = d[2] + ',';
    return [d[0], d[1], d[2], d[3]].join(' ');
}

var formateDate = Date.parse(new Date());
var today = getDate(formateDate);

var date=today.slice(8,10);
var month=today.slice(4,7);
var year=today.slice(12,16);
var reFormatedDate = date+" "+month+" "+year;
		
	var tokenRejectDetails = tokenRejectExcelDownloadResult.content;
	
	
	// Create a new workbook
const workbook = XLSX.utils.book_new();

// Convert the array to a worksheet
const worksheet = XLSX.utils.aoa_to_sheet(tokenRejectDetails);

// Add the worksheet to the workbook
XLSX.utils.book_append_sheet(workbook, worksheet, 'My Sheet');

// Write the workbook to a file
XLSX.writeFile(workbook, 'TokenReject_'+reFormatedDate+'.xlsx');
	
	}else{}
	
})
})
}

(window.location.pathname == "/etiicos/etiicosHome")
{
function stateDwn(){

$.get("innerprocess/etiicos/stateexceldownloader",function(analyzerStateExcelDownloadResult){
	
	if(analyzerStateExcelDownloadResult.status == 200 && analyzerStateExcelDownloadResult.message == 'success')
	{

//ReFormate Database Date --> Start 
function getDate(formateDate) {
    var d = (new Date(formateDate) + '').split(' ');
    d[2] = d[2] + ',';
    return [d[0], d[1], d[2], d[3]].join(' ');
}

var formateDate = Date.parse(new Date());
var today = getDate(formateDate);

var date=today.slice(8,10);
var month=today.slice(4,7);
var year=today.slice(12,16);
var reFormatedDate = date+" "+month+" "+year;
		
	var stateDetails = analyzerStateExcelDownloadResult.content;
	
	
	// Create a new workbook
const workbook = XLSX.utils.book_new();

// Convert the array to a worksheet
const worksheet = XLSX.utils.aoa_to_sheet(stateDetails);

// Add the worksheet to the workbook
XLSX.utils.book_append_sheet(workbook, worksheet, 'My Sheet');

// Write the workbook to a file
XLSX.writeFile(workbook, 'StateAnalyzer_'+reFormatedDate+'.xlsx');
	
	}else{}
	
})
}

function statelistDwn(stateValue){

var StateDwnValue = stateValue.getAttribute("value");

$.post("innerprocess/etiicos/statelistexceldownloader",{state:StateDwnValue},function(analyzerStateListExcelDownloadResult){
	
	if(analyzerStateListExcelDownloadResult.status == 200 && analyzerStateListExcelDownloadResult.message == 'success')
	{

//ReFormate Database Date --> Start 
function getDate(formateDate) {
    var d = (new Date(formateDate) + '').split(' ');
    d[2] = d[2] + ',';
    return [d[0], d[1], d[2], d[3]].join(' ');
}

var formateDate = Date.parse(new Date());
var today = getDate(formateDate);

var date=today.slice(8,10);
var month=today.slice(4,7);
var year=today.slice(12,16);
var reFormatedDate = date+" "+month+" "+year;
		
	var stateListDetails = analyzerStateListExcelDownloadResult.content;
	
	// Create a new workbook
const workbook = XLSX.utils.book_new();

// Convert the array to a worksheet
const worksheet = XLSX.utils.aoa_to_sheet(stateListDetails);

// Add the worksheet to the workbook
XLSX.utils.book_append_sheet(workbook, worksheet, 'My Sheet');

// Write the workbook to a file
XLSX.writeFile(workbook, StateDwnValue+'_'+reFormatedDate+'.xlsx');
	
	}else{}
	
})
}

function cityDwn(){

$.get("innerprocess/etiicos/cityexceldownloader",function(analyzerCityExcelDownloadResult){
	
	if(analyzerCityExcelDownloadResult.status == 200 && analyzerCityExcelDownloadResult.message == 'success')
	{

//ReFormate Database Date --> Start 
function getDate(formateDate) {
    var d = (new Date(formateDate) + '').split(' ');
    d[2] = d[2] + ',';
    return [d[0], d[1], d[2], d[3]].join(' ');
}

var formateDate = Date.parse(new Date());
var today = getDate(formateDate);

var date=today.slice(8,10);
var month=today.slice(4,7);
var year=today.slice(12,16);
var reFormatedDate = date+" "+month+" "+year;
		
	var cityDetails = analyzerCityExcelDownloadResult.content;
		
	// Create a new workbook
const workbook = XLSX.utils.book_new();

// Convert the array to a worksheet
const worksheet = XLSX.utils.aoa_to_sheet(cityDetails);

// Add the worksheet to the workbook
XLSX.utils.book_append_sheet(workbook, worksheet, 'My Sheet');

// Write the workbook to a file
XLSX.writeFile(workbook, 'CitiesAnalyzer_'+reFormatedDate+'.xlsx');
	
	}else{}
	
})
}

function cityListDwn(cityValue)
{
	var CityDwnValue = cityValue.getAttribute("value");


$.post("innerprocess/etiicos/citylistexceldownloader",{city:CityDwnValue},function(analyzerCityListExcelDownloadResult){
	
	if(analyzerCityListExcelDownloadResult.status == 200 && analyzerCityListExcelDownloadResult.message == 'success')
	{

//ReFormate Database Date --> Start 
function getDate(formateDate) {
    var d = (new Date(formateDate) + '').split(' ');
    d[2] = d[2] + ',';
    return [d[0], d[1], d[2], d[3]].join(' ');
}

var formateDate = Date.parse(new Date());
var today = getDate(formateDate);

var date=today.slice(8,10);
var month=today.slice(4,7);
var year=today.slice(12,16);
var reFormatedDate = date+" "+month+" "+year;
		
	var cityListDetails = analyzerCityListExcelDownloadResult.content;
	
	
	// Create a new workbook
const workbook = XLSX.utils.book_new();

// Convert the array to a worksheet
const worksheet = XLSX.utils.aoa_to_sheet(cityListDetails);

// Add the worksheet to the workbook
XLSX.utils.book_append_sheet(workbook, worksheet, 'My Sheet');

// Write the workbook to a file
XLSX.writeFile(workbook, CityDwnValue+'_'+reFormatedDate+'.xlsx');
	
	}else{}
	
})
}

function departmentDwn()
{
	$.get("innerprocess/etiicos/analyzerdepartmexceldownloader",function(analyzerdepartmentExcelDownloadResult){
	
	if(analyzerdepartmentExcelDownloadResult.status == 200 && analyzerdepartmentExcelDownloadResult.message == 'success')
	{

//ReFormate Database Date --> Start 
function getDate(formateDate) {
    var d = (new Date(formateDate) + '').split(' ');
    d[2] = d[2] + ',';
    return [d[0], d[1], d[2], d[3]].join(' ');
}

var formateDate = Date.parse(new Date());
var today = getDate(formateDate);

var date=today.slice(8,10);
var month=today.slice(4,7);
var year=today.slice(12,16);
var reFormatedDate = date+" "+month+" "+year;
		
	var departmentDetails = analyzerdepartmentExcelDownloadResult.content;
	
	// Create a new workbook
const workbook = XLSX.utils.book_new();

// Convert the array to a worksheet
const worksheet = XLSX.utils.aoa_to_sheet(departmentDetails);

// Add the worksheet to the workbook
XLSX.utils.book_append_sheet(workbook, worksheet, 'My Sheet');

// Write the workbook to a file
XLSX.writeFile(workbook, 'DepartmentAnalyzer_'+reFormatedDate+'.xlsx');
	
	}else{}
	
})
}

function departmentlistDwn(departmentValue)
{
	var departmentDwnValue = departmentValue.getAttribute("value");


$.post("innerprocess/etiicos/analyzerdepartmeListxceldownloader",{department:departmentDwnValue},function(analyzerDeptmListExcelDownloadResult){
	
	if(analyzerDeptmListExcelDownloadResult.status == 200 && analyzerDeptmListExcelDownloadResult.message == 'success')
	{

//ReFormate Database Date --> Start 
function getDate(formateDate) {
    var d = (new Date(formateDate) + '').split(' ');
    d[2] = d[2] + ',';
    return [d[0], d[1], d[2], d[3]].join(' ');
}

var formateDate = Date.parse(new Date());
var today = getDate(formateDate);

var date=today.slice(8,10);
var month=today.slice(4,7);
var year=today.slice(12,16);
var reFormatedDate = date+" "+month+" "+year;
		
	var departmentListDetails = analyzerDeptmListExcelDownloadResult.content;
	
	
	// Create a new workbook
const workbook = XLSX.utils.book_new();

// Convert the array to a worksheet
const worksheet = XLSX.utils.aoa_to_sheet(departmentListDetails);

// Add the worksheet to the workbook
XLSX.utils.book_append_sheet(workbook, worksheet, 'My Sheet');

// Write the workbook to a file
XLSX.writeFile(workbook, departmentDwnValue+'_'+reFormatedDate+'.xlsx');
	
	}else{}
	
})
	
}

}

