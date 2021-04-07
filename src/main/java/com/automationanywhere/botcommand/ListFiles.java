package com.automationanywhere.botcommand;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.DictionaryValue;
import com.automationanywhere.botcommand.data.impl.ListValue;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Execute;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.rules.MatchesRegex;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;
import static com.automationanywhere.commandsdk.model.DataType.*;

@BotCommand
@CommandPkg(label = "SubfolderFiles", name = "SubfolderFiles", property_name = "Returned the files which contained on specific subfolders of the parent\n @author Amro Ezzat", description = "Returned the files which contained on specific subfolder of the parent \n @author Amro Ezzat", icon = "pkg.svg",
        node_label = "Returned the files which contained on specific subfolders of the parent\n @author Amro Ezzat",
        return_type = LIST,return_sub_type = ANY, return_label = "Assign the output to list variable (Subtype must be Any)", return_required = true)

public class ListFiles {
    @Execute
    public ListValue<String> action(@Idx(index = "1", type = TEXT) @Pkg(label = "Parent folder path", description = "Enter the parent folder path", default_value_type = STRING)@MatchesRegex(".*[Aa-zZ|1-9|:]$")  @NotEmpty String folderPath,
                                    @Idx(index = "2", type = TEXT) @Pkg(label = "File extension", description = "Enter the file extension which searched for OR '*' for listing all files", default_value_type = STRING)@MatchesRegex("^((?!\\.).)*$") @NotEmpty String fileExtension) {
        List<String> paths = new ArrayList<>(); // to get all paths
        ListValue<String> FinalPaths = new ListValue<String>();// for returning type
        List<Value> temp = new ArrayList<>(); // for casting from paths <String> to <Value>
        try {
            final File folder = new File(folderPath);
            search(".*\\." + fileExtension, folder, paths);
            // transferring data
            for (int i = 0; i < paths.size(); i++) {
                Value v = new StringValue(paths.get(i));
                temp.add(v);
            }
            FinalPaths.set(temp);
        } catch (Exception var13) {
            throw new BotCommandException(var13.getMessage(), var13);
        }
        return FinalPaths;
    }

    //search function
    public static void search(final String pattern, final File folder, List<String> result) {
        for (final File f : folder.listFiles()) {

            if (f.isDirectory()) {
                search(pattern, f, result);
            }

            if (f.isFile()) {
                if (f.getName().matches(pattern)) {
                    result.add(f.getAbsolutePath());
                }
            }

        }
    }
}