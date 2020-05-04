var FACING_MODES = JslibHtml5CameraPhoto.FACING_MODES; 
var IMAGE_TYPES = JslibHtml5CameraPhoto.IMAGE_TYPES;

var videoElement = document.getElementById('videoId');
var btnPhoto = document.querySelector('.main-circle');
var imgPhoto = document.createElement('img');

var cameraPhoto = new JslibHtml5CameraPhoto.default(videoElement);

function startCameraDefaultResolution () {
	var facingMode = "ENVIRONMENT";
	cameraPhoto.startCamera(FACING_MODES[facingMode])
	.then(() => {
		/* alert('Tudo OK');*/
	})
	.catch((error) => {
		console.error('Camera not started!', error);
		alert('Falha ao iniciar a câmera! Usa o Chrome? Concedeu permissão?', error)
	});
}

function takePhoto () { 
	btnPhoto.onclick = null; 

	var sizeFactor = 1;
	var imageType = IMAGE_TYPES.JPG;
	var imageCompression = 1;

	var config = {
		sizeFactor,
		imageType,
		imageCompression
	};

	// Conteudo da foto
	var dataUri = cameraPhoto.getDataUri(config); 
	// Envia para backend
	try {
		sendRequest(dataUri);		
	} catch (e) {
		alert('Falha ao enviar imagem: ', e);
	}	

	// Para camera e mostra imagem
	stopCamera();
	imgPhoto.src = dataUri;
	imgPhoto.classList.add('img-fluid');
	
	var divCamera = document.querySelector('#divCamera');
	divCamera.innerHTML = '';
	divCamera.appendChild(imgPhoto);  
	document.querySelector('.overlay').classList.add('overlay-escuro');
	 
}

function closeButtons() {
	$(".other-circles").toggleClass("other-circles-active");
	document.querySelector('.overlay').classList.remove('overlay-escuro');

	var divCamera = document.querySelector('#divCamera');
	divCamera.innerHTML = '<video id="videoId" class="embed-responsive-item" autoplay="true" playsInline></video>';
	
	videoElement = document.getElementById('videoId');
	cameraPhoto = new JslibHtml5CameraPhoto.default(videoElement);
	imgPhoto = document.createElement('img');

	btnPhoto.onclick = takePhoto; 
	startCameraDefaultResolution();
	
}

function stopCamera () {
	cameraPhoto.stopCamera();
}   

function showLoader() {
	var divLoader = document.querySelector('#loaderContainer');
	divLoader.classList.remove('oculto');
}    

function hideLoader() {
	var divLoader = document.querySelector('#loaderContainer');
	divLoader.classList.add('oculto');
}    

function sendRequest(data) {
	var token = $('meta[name=_csrf]').attr('content');
	var header = $('meta[name=_csrf_header]').attr('content');

	$.ajax({
		type: "post",
		url: "/processarFoto",
		data: { content: JSON.stringify(data)},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
			//alert('Aguarde retorno da análise');
			showLoader();
		},        		
		success: function(data) {        			
			hideLoader();

			if (data.length == 0) {
				alert('Erro no retorno');
				return;
			}

			// Carregar os itens
			for (var i = 0; i < 5; i++) {
				var texto = data[i];
				var url = "https://www.americanas.com.br/busca/" + texto;
				document.querySelector('.circle.circle'+(i+1)+'>.show-text>.circle-text').innerHTML = 
					"<a href='"+url+"'>"+texto+"</a>";
			}

			$(".other-circles").toggleClass("other-circles-active");

			btnPhoto.onclick = closeButtons; 
		}        	
	});        	
}  

document.addEventListener('DOMContentLoaded', function () {
	btnPhoto.onclick = takePhoto; 
			
	startCameraDefaultResolution();
});