$(document).ready(function() { 
	var tabs = $( "#tabs" );
	tabs.tabs();
	
	//Imposto il binding del click per le righe di deploy
	$(".deploy_link").live('click', function(event)
	{
		event.preventDefault();
		//Richiamo il comando nell'href e aggiungo in coda il contenuto al box #shell_container
		$.ajax($(this).attr('href'),
		{ success: function(data, textStatus, jqXHR)
			{
				$('#runUpdateApplResponse').append(data);
				var scroll = $('#shell_container').jScrollPane(
						{
							showArrows:true,
							maintainPosition: false
						});
				scroll.data('jsp').scrollToPercentY(100);
			}
		});
	});
	
	//Prelevo il request id
	var requestID  = $("#requestID").val();

	
	
	//Faccio il binding della pressione sul pulsante per fare l'aggiornamento
	$("#run_update").click( function(event) {
		event.preventDefault();
		
		//Sostituisco il pulsante con due div annidati
		$('#form_container').replaceWith('<div id="update_operation"><div id="shell_container"></div></div>');
		
		//Richiamo la procedura che effettua l'update inviando l'id della richiesta
		$.ajax('RunUpdate.do',
		{
			data: {'requestID': requestID}, 
			beforeSend: function(jqXHR, settings)
			{
				var loadingBar = '<img src="style/images/blue_loader.gif" /> Sto richiamando lo script remoto';
				$('#shell_container').append(loadingBar);
			},
			success: function(data, textStatus, jqXHR)
			{
				$('#shell_container').empty();
				$('#shell_container').append(data);
					
			},
			complete: function(data, textStatus, jqXHR)
			{
				var scroll = $('#shell_container').jScrollPane(
				{
					showArrows:true,
					maintainPosition: false
				});
				scroll.data('jsp').scrollToPercentY(100);
			}
		});

	});
});