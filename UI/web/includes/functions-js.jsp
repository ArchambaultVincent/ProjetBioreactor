<%--
  Created by IntelliJ IDEA.
  User: clort
  Date: 13/05/2020
  Time: 13:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    // Manuel mode
    $('#list-man').on('click', function () {
        // ENABLE: a man list, man text, man form
        $('#list-man').addClass('active')
        $('#text-man').css('display','block')
        // Form
        // DISABLE: a auto list, a text, a form
        $('#list-auto').removeClass('active')
        $('#text-auto').css('display','none')
    })

    // Auto mode
    $('#list-auto').on('click', function () {
        // ENABLE: a auto list, a text, a form
        $('#list-auto').addClass('active')
        $('#text-auto').css('display','block')
        // Form
        // DISABLE: a man list, man text, man form
        $('#list-man').removeClass('active')
        $('#text-man').css('display','none')
    })

    // Switches for IA
    let opti = $('#switchOptimisation')
    let fdi = $('#switchFaultDetection')

    // Get the values in the cookies
    $('#switchOptimisation').val(0)
    $('#switchFaultDetection').val(0)

    /* ==== FUNCTIONS ==== */
    /* Change the value of INPUT HIDDEN of valueOptimisation */
    function getSwitchOpti() {
        if ($('#switchOptimisation').is(":checked")) {
            $('#valueOptimisation').val(1)
        } else {
            $('#valueOptimisation').val(0)
        }
    }
    /* Change the value of INPUT HIDDEN of valueFaultDetection */
    function getSwitchFault() {
        $('#switchFaultDetection').val()

        if ($('#switchFaultDetection').is(":checked")) {
            $('#valueFaultDetection').val(1)
        } else {
            $('#valueFaultDetection').val(0)
        }
    }
</script>