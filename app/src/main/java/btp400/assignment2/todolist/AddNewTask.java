package btp400.assignment2.todolist;

import static android.view.WindowManager.*;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

import btp400.assignment2.todolist.logger.UILogger;
import btp400.assignment2.todolist.model.ToDoModel;
import btp400.assignment2.todolist.utils.DatabaseHandler;

/**
 * <h1>AddNewTask</h1>
 * this class is in charge of handling the new tasks that get added by the user*/
public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";

    private EditText newTaskText;
    private Button newTaskSaveButton;
    private DatabaseHandler db;

    /**
     *this method is used for returning an object of AddNewTask and gives us the ability to access other methods form this class in other classes*/
    public static AddNewTask newInstance() {
        return new AddNewTask();
    }
    /**
    * this method is used for initializing the creation of a fragment*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }
    /**
     *this method the view hierarchy linked with the fragment is created and returned.*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_task, container, false);
        Objects.requireNonNull(getDialog()).getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return view;
    }
    /**
     * in this method we will define the necessary code for executing the functions for our dialog fragment.*/
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = requireView().findViewById(R.id.editTextNewTask);
        newTaskSaveButton = requireView().findViewById(R.id.buttonNewTask);

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();

        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            if (task.length() > 0) {
                newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            }
        }
        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    newTaskSaveButton.setEnabled(false);
                    newTaskSaveButton.setTextColor(Color.DKGRAY);
                } else {
                    newTaskSaveButton.setEnabled(true);
                    newTaskSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final boolean finalIsUpdate = isUpdate;
        newTaskSaveButton.setOnClickListener(view1 -> {
            UILogger.onClick(TAG, "Save button clicked");
            String text = newTaskText.getText().toString();
            if (!text.equals("")) {
                if (finalIsUpdate) {
                    db.updateTask(bundle.getInt("id"), text);
                } else {
                    ToDoModel task = new ToDoModel();
                    task.setTask(text);
                    task.setStatus(0);
                    db.insertTask(task);
                }
                dismiss();
            }
        });
    }
    /**this function is called When the fragment is no longer associated to its activity.*/
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }
}
