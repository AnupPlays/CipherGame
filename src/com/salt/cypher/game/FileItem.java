package com.salt.cypher.game;

public class FileItem {
    // delete this:
    public static void main(String[] args) {
        FileItem root = new Directory("admin",
                new Directory("homework",
                        new File("math.txt", "1+1=2"),
                        new File("physics.txt", "F=ma")
                ),
                new File("documents.txt", "hi anup"),
                new File("passwords.txt", "admin"),
                new File("user_secrets.txt", "ur mum gay")
        );

        System.out.println(root);
    }
    public String name;
    public Directory parent;

    public String toString() {
        return toString(0);
    }

    public String toString(int indent) {
        return null;
    }

    public static class Directory extends FileItem {
        public FileItem[] files;

        public Directory(String name, FileItem... files) {
            super.name = name;
            this.files = files;

            super.parent = null;
            for (FileItem file : files) file.parent = this;
        }

        public String toString(int indent) {
            String str = "";

            str += " ".repeat(indent) + "/" + name;
            for (FileItem file : files) {
                str += "\n" + file.toString(indent + 1);
            }

            return str;
        }
    }

    public static class File extends FileItem {
        public String data;

        public File(String name, String data) {
            super.name = name;
            super.parent = null;
            this.data = data;
        }

        public String toString(int indent) {
            return "\t".repeat(indent) + name;
        }
    }
}