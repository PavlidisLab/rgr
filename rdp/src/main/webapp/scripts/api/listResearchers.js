var jsonToResearcherTable = function(response, tableId) {
   $.each( response, function(i, item) {
      $( '<tr>' ).append( $( '<td>' ).text( item.username ), $( '<td>' ).text( item.email ),
         $( '<td>' ).text( item.firstName ), $( '<td>' ).text( item.lastName ), $( '<td>' ).text( item.organization ),
         $( '<td>' ).text( item.department ) ).appendTo( tableId );
      // $('#records_table').append($tr);
      // console.log($tr.wrap('<p>').html());
   } );
};

var showResearchers = function() {

   // get this from a controller
   var response = '[{"username":"testUsername2", "email":"testEmail2", "firstName":"testFirstname2", "lastName":"testLastname2", "organization":"testOrganization2", "department":"testDepartment2" }]';
   response = $.parseJSON( response );
   var tableId = "#listResearchersTable";
   jsonToResearcherTable( response, tableId );

   $( "#listResearchersTable" ).dataTable();
   $( '#registerTab a[href="#registeredResearchers"]' ).show();

   // username, email, first name, last name, organization, department
};

$( document ).ready( function() {

   $( "#navbarUsername" ).on( "loginSuccess", function(event, response) {
      // Only show Researchers tab if current user is "admin"
      if ( jQuery.parseJSON( response ).isAdmin == "true" ) {
         showResearchers();
      }
   } );

} );