package ctf.worldcorp.collaborators;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readString;

public class Main {

    private static final String WORLDCORP_BASE_PATH = "/opt/worldcorp/collaborator";

    public static void main(String[] args) throws IOException {
        System.out.println("Fetch new collaborators");
        List<String> imagePathList = recoverImagesPathsFrom(WORLDCORP_BASE_PATH + "/new");

        System.out.println("Recover images for collaborators");
        List<File> imageFileList = recoverImages(imagePathList);

        System.out.println("Recover xml details for collaborators");
        List<String> collaboratorDetailsXmlFilesPaths = mapCollaboratorDetailsXmlFilePath(imageFileList);

        // Do The Stuff with the xml files
        System.out.println("TODO: Generate the html page for collaborators");
        for (String xmlPath : collaboratorDetailsXmlFilesPaths) {
            ParseCollaboratorXml collaboratorParser = new ParseCollaboratorXml(xmlPath);
            Collaborator collaborator = collaboratorParser.parseContent();

            // TODO: Generate HTML Page for Collaborator

            collaboratorParser.updateCollaboratorFile();
        }

        // clear new_collaborators directory
        System.out.println("Clear new collaborators directory");
        clearDirectory(WORLDCORP_BASE_PATH + "/new");
    }

    private static void clearDirectory(String directoryLocation) {
        File directory = new File(directoryLocation);

        if (!directory.exists()) {
            return;
        }

        List<File> fileInDir = Arrays.stream(Objects.requireNonNull(directory.list()))
                .filter(file -> file.endsWith(".txt"))
                .map(filename -> directoryLocation + "/" + filename)
                .map(File::new)
                .toList();

        fileInDir.forEach(File::delete);
    }

    private static List<String> mapCollaboratorDetailsXmlFilePath(List<File> imageFileList) {
        return imageFileList.stream()
                .map(Main::extractMetadataList)
                .map(Main::authorTagContent)
                .map(Main::collaboratorXmlDestFile)
                .toList();
    }

    private static String collaboratorXmlDestFile(String collaboratorName) {
        return WORLDCORP_BASE_PATH + "/xml/" + collaboratorName + "_description.xml";
    }

    private static String authorTagContent(List<Tag> imageMetadataTags) {
        Optional<Tag> authorTag = imageMetadataTags.stream()
                .filter(t -> t.getDescription().startsWith("Author: "))
                .findFirst();

        if (authorTag.isEmpty()) {
            return "";
        }

        String authorTagContent = authorTag.get().getDescription();

        Pattern authorPattern = Pattern.compile("^Author:\\s(?<author>.+)");
        Matcher matcher = authorPattern.matcher(authorTagContent);

        if (!matcher.find()) {
            return "";
        }

        return matcher.group("author");
    }

    private static List<Tag> extractMetadataList(File image) {
        try {

            Metadata metadata = ImageMetadataReader.readMetadata(image);
            List<Tag> tagList = new ArrayList<>();

            metadata.getDirectories()
                    .forEach(d -> tagList.addAll(d.getTags()));

            return tagList;
        } catch (ImageProcessingException | IOException e) {
            return new ArrayList<>();
        }
    }

    private static List<File> recoverImages(List<String> imagePathList) {
        return imagePathList.stream()
                .map(File::new)
                .filter(File::exists)
                .toList();
    }

    private static List<String> recoverImagesPathsFrom(String directoryLocation) throws IOException {
        File directory = new File(directoryLocation);
        if (!directory.exists()) {
            return List.of();
        }
        return Arrays.stream(Objects.requireNonNull(directory.list()))
                .filter(file -> file.endsWith(".txt"))
                .map(filename -> directoryLocation + "/" + filename)
                .map(Paths::get)
                .map(Main::readContent)
                .filter(content -> !content.isEmpty())
                .toList();
    }

    private static String readContent(Path filePath) {
        try {
            return readString(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }
}