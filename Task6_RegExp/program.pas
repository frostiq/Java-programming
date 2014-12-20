Program comments;
  Var a,b:Integer;  // тут строчный комментарий
  Begin
    // WriteLn('Введите число');
	a:=0;
	b:=10;
	for a:=1 to 10 do
	  begin
	    Write (a*b, ' ');
	  end;
	  {  Это блочный комментарий
	for a:=1 to 10 do
	  begin
	    Write (a*b, ' ');
	  end;
	 все что внутри не исполняется}
	WriteLn('        {fffff  }          ');
  End.