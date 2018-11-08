<!-- Modal -->
<button id="tagsModal" class="btn btn-primary btn-lg hidden" data-toggle="modal" data-target="#tagsModalContainer"></button>
<div class="modal fade" id="tagsModalContainer" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">Tags del movimiento</h4>
            </div>
            <div class="modal-body">
                <input type="hidden" id="movAddTagId" value="" />
                <g:each in="${tags}" var="tag">
                    <a onclick="movementListController.clickAddTag(this, ${tag.id});" id="modal_tag_${tag.id}"
                       class="btn btn-${(tag.id in mov?.tags*.id) || (tag.id in tagsFilter) ? "success" : "primary"} btn-tag btn-block"
                       style="margin-bottom: 5px;"
                       tag-id="${tag.id}"
                       tag-detail="${tag.detail}">
                        <i class="fa fa-tag"></i> ${tag.detail}
                    </a>
                </g:each>
            </div>
            <div class="modal-footer">
                <button id="btnSaveAddTag" type="button" class="btn btn-primary" onclick="movementListController.refresh()"><i
                        class="fa fa-check"></i> Guardar</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->