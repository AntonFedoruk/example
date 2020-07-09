<#-- Make form of adding  message to be possible to hide and open by clicking the link(button) -->
<a class="btn btn-primary mb-2" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
   aria-controls="collapseExample">         <#--                   /\  -->
    Message editor                          <#--                   |   -->
</a>                                        <#--                   |   -->
<div class="collapse <#if message??>show</#if>" <#--               |   -->
     id="collapseExample"> <#-- use this 'id' to connect form with "button"; class="collapse": used to show and hide content-->
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" class="form-control ${(textError??)?string('is-invalid','')}"
                       value="<#if message??>${message.text}</#if>" name="text" placeholder="Введіть повідомлення"/> <#--is-invalid: throw textError to 'div class="invalid-feedback"'-->
                <#-- if block of validation-->
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text" class="form-control"
                       value="<#if message??>${message.tag}</#if>" name="tag" placeholder="Тег"/>
                <#-- if block of validation-->
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Save message</button>
            </div>
        </form>
    </div>
</div>