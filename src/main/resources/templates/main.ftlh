<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" class="form-control mr-2" name="filter" value="${filter?ifExists}"
                       placeholder="Search by tag">
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>
    </div>
<#-- Make form of adding  message to be possible to hide and open by clicking the link(button) -->
    <a class="btn btn-primary mb-2" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">         <#--                   /\  -->
        Add new Message                         <#--                   |   -->
    </a>                                        <#--                   |   -->
    <div class="collapse"                       <#--                   |   -->
         id="collapseExample"> <#-- use this 'id' to connect form with "button"; class="collapse": used to show and hide content-->
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control" name="text" placeholder="Введіть повідомлення"/>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="tag" placeholder="Тег"/>
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" class="custom-file-input" id="customFile">
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </div>
            </form>
        </div>
    </div>
    <div class="card-columns">
    <#list messages as message>
        <div class="card my-3 "> <#--padding top and bottom by 3-->
            <div>
                <#if message.filename??>
                    <img class="card-img-top" src="/img/${message.filename}">
                </#if>
            </div>
            <div class="m-2"> <#--margin from all sides-->
                <span>${message.text}</span>
                <i>${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
               ${message.authorName}
            </div>
        </div>
    <#else>
        No messages :(
    </#list>
    </div>
</@c.page>