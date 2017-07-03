<g:if test="${alert && alert.text}">
    <div class="alert alert-${alert.type}">
        <button type="button" class="close" aria-hidden="true">×</button>
        <g:if test="${alert && alert.text}"><i class="fa ${alert.icon}"></i></g:if> ${raw(alert.text)}
    </div>
</g:if>