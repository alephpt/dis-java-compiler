package dev.alephpt.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAST {
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.err.println("Usage: generate_ast <output directory>");
      System.exit(64);
    }
    String outputDir = args[0];
    
    defineAST(outputDir, "Express", Arrays.asList(
          "Binary   : Express left, Token Operator, Express right",
          "Grouping : Express expression",
          "Literal  : Object value",
          "Unary    : Token operator, Express right"
          )
    );
  }

  private static void defineAST(String outputDir, String baseName, List<String> types) throws IOException {
    String path = outputDir + "/" + baseName + ".java";
    PrintWriter writer = new PrintWriter(path, "UTF-8");

    writer.println("package dev.alephpt.Dis;");
    writer.println();
    writer.println("import java.util.List;");
    writer.println();
    writer.println("abstract class " + baseName + " {");

    // the AST Classes
    for (String type : types) {
      String className = type.split(":")[0].trim();
      String fields = type.split(":")[1].trim();
      defineType(writer, baseName, className, fields);
    }

    writer.println("}");
    writer.close();
  }

  private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
    writer.println();
    // declaration
    writer.println("  static class " + className + " extends " + baseName + " {");

    // constructor
    writer.println("    " + className + "(" + fieldList + ") {");

    // parameters in fields
    String[] fields = fieldList.split(", ");
    for (String field : fields) {
      String name = field.split(" ")[1];
      writer.println("      this." + name + " = " + name + ";"); 
    }

    writer.println("    }");

    // fields
    writer.println();
    for (String field : fields) {
      writer.println("    final " + field + ";");
    }

    writer.println("  }");
  }
}

