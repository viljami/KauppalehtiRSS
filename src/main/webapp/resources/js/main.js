var requestInterval = null;
var loadingElement = '<img id="loading" src="../resources/img/loading.gif" alt="Loading..." >';

$(function() {
	if ( $('#content').children().length <= 0 ) {
		$('#content').append( loadingElement );
		requestInterval = setInterval( feedItemsLoader, 2000 );
	}
});

var feedItemsLoader = function ( e ) {
	var url = location.origin + '/KauppalehtiRSS/spring/feedItems';
	
	$.getJSON(url, function( json ) {
		if ( json.length > 0) {
			$('#loading').remove();
		} else {
			return;
		}
		
		var feedItems = [];
		
		$.each(json, function(index, val) {
			feedItems.push('<div class="media"><div class="media-body">' + 
				'<h4 class="media-heading"><a class="feedItemTitle" href="' + 
				val.link + '" >' + 
				val.title + '</a></h4><blockquote><p>' +
				val.description + '<small>' +
				val.category + '-' + 
				val.date + '</small></p></blockquote></div></div>'
			);
		});

		$('#content').append( feedItems.join('') );
		
		if( json.length < 100 ) {
			$('#content').append( loadingElement );
		} else {
			clearInterval( requestInterval );
			console.log("main.js - Request interval cleared.");
		}
	});	
};
