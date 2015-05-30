function edit(id) {
    var rows = $('tr#' + id).children();
    var edit = $('form#edit');
    edit.find('[name=id]').val(rows[0].innerText);
    edit.find('[name=name]').val(rows[1].innerText);
    edit.find('[name=price]').val(rows[2].innerText);
    edit.find('[name=quantity]').val(rows[3].innerText);
    edit.find('[name=storageId]').val(rows[4].innerText);

    edit.show();
}