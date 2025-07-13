Dim grid(15,15)
Dim x, y, response, i, j, line
Dim fso, file

x = 0
y = 0

Do
    ' Clear grid and set player position
    For j = 0 To 15
        For i = 0 To 15
            grid(i,j) = 0
        Next
    Next
    grid(x,y) = 1

    ' Save grid to file
    Set fso = CreateObject("Scripting.FileSystemObject")
    Set file = fso.CreateTextFile("p_map.parr", True)

    For j = 0 To 15
        line = ""
        For i = 0 To 15
            line = line & grid(i,j)
        Next
        file.WriteLine line
    Next
    file.Close

    ' Prompt for movement
    response = MsgBox("Yes=Left, No=Right, Cancel=Down", vbYesNoCancel + vbQuestion, "example_game_01.vbs")

    ' Handle input
    If response = vbYes Then
        If x > 0 Then x = x - 1
    ElseIf response = vbNo Then
        If x < 15 Then x = x + 1
    ElseIf response = vbCancel Then
        If y < 15 Then y = y + 1
    Else
        Exit Do
    End If
Loop
