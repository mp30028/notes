The PowerShell console includes some basic editing capabilities for the current line. In addition, you can enter get-history to list all the commands in the command history, or enter clear-history to clear the command history. Get-History lists commands by command number, and you can pass this to Invoke-History to run a specific numbered command from your command history. For example, this would run command 35:
invoke-history 35

Here is a list of useful line editing commands supported by Windows PowerShell:

` [Backward apostrophe key] Press the backward apostrophe key to insert a line break or as an escape character to make a literal character. You can also break a line at the pipe (|) character.

Alt+Space+E Displays an editing shortcut menu with Mark, Copy, Paste, Select All, Scroll, and Find options. You can then press K for Mark, Y for Copy, P for Paste, S for Select All, L to scroll through the screen buffer, or F to search for text in the screen buffer. To copy the screen buffer to the Clipboard, press Alt+Space+E+S and then press Alt+Space+E+Y.

Alt+F7 Clears the command history.

Ctrl+C Press Ctrl+C to break out of the subprompt or terminate execution.

Ctrl+End Press Ctrl+End to delete all the characters in the line after the cursor.

Ctrl+Left arrow / Ctrl+Right arrowPress Ctrl+Left arrow or Ctrl+Right arrow to move left or right one word at a time.

Ctrl+S Press Ctrl+S to pause or resume the display of output.

Delete / Backspace Press Delete to delete the character under the cursor, or press the Backspace key to delete the character to the left of the cursor.

Esc Press the Esc key to clear the current line.

F1 Moves the cursor one character to the right on the command line. At the end of the line, inserts one character from the text of your last command.

F2 Creates a new command line by copying your last command line up to the character you type.

F3 Completes the command line with the content from your last command line, starting from the current cursor position to the end of the line.

F4 Deletes characters from your current command line, starting from the current cursor position up to the character you type.

F5 Scans backward through your command history.

F7 Displays a pop-up window with your command history and allows you to select a command. Use the arrow keys to scroll through the list. Press Enter to select a command to run, or press the Right arrow key to place the text on the command line.

F8 Uses text you’ve entered to scan backward through your command history for commands that match the text you’ve typed so far on the command line.

F9 Runs a specific numbered command from your command history. Command numbers are listed when you press F7.

Home / End Press Home or End to move to the beginning or end of the line.

Insert Press Insert to switch between insert mode and overwrite mode.

Left / Right arrow keys Press the Left or Right arrow key to move the cursor left or right on the current line.

Page Up / Page Down Press the Page Up or Page Down key to access the first or last command in the command history.

Right-click If QuickEdit is disabled, displays an editing shortcut menu with Mark, Copy, Paste, Select All, Scroll, and Find options. To copy the screen buffer to the Clipboard, right-click, choose Select, and then press Enter.

Tab / Shift+Tab Press the Tab key or press Shift+Tab to access the tab expansion function.

Up / Down arrow keys Press the Up or Down arrow key to scan forward or backward through your command history.
