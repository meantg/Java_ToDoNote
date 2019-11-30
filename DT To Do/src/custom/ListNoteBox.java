package custom;

import DTO.NoteDTO;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;
import java.util.List;

public class ListNoteBox {
    private List<NoteBox> listNoteBox;

    public ListNoteBox(List<NoteDTO> dsNote, TilePane noteboxPane) {
        listNoteBox = new ArrayList<NoteBox>();

        dsNote.stream().forEach(note -> {
            NoteBox noteBox = new NoteBox(note);
            noteBox.setPrefWidth(noteboxPane.getPrefWidth() - noteboxPane.getPadding().getLeft() - noteboxPane.getPadding().getRight());
            listNoteBox.add(noteBox);
        });
    }

    public List<NoteBox> getList() {
        return listNoteBox;
    }
}
