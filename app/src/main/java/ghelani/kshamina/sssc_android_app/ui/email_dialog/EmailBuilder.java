package ghelani.kshamina.sssc_android_app.ui.email_dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import ghelani.kshamina.sssc_android_app.MainApplication;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.entity.Event;

public class EmailBuilder {

    public enum EmailType {
        MENTOR_BOOKING, EVENT_REGISTRATION
    }

    public static void sendEventBookingEmail(Activity context, String studentName, String studentId, String degree, Event event) {
        String[] recipients = {"sssc@carleton.ca"};
        String subject = "SSSC Event Booking: " + event.getName();
        String message = "Hello," +
                "\n\nI would like to register for " + event.getName() + " on" + event.getDateDisplayStringSingle() + "." + "\n\n" +
                "\nName: " + studentName +
                "\nStudent Number: " + studentId +
                "\nDegree: " + degree +
                "\nThank you!" +
                "\n" + studentName +
                "\n" + studentId;
        sendEmail(context, recipients, subject, message);
    }

    public static void sendMentorBookingEmail(Activity context, String studentName, String studentId, String degree) {
        String[] recipients = {"sssc@carleton.ca"};
        String subject = "SSSC Mentor Appointment";
        String message = "Hello," +
                "\n\nI would like to book an appointment with an SSSC mentor for.\n" +
                "\nName: " + studentName +
                "\nStudent Number: " + studentId +
                "\nDegree: " + degree +
                "\nThank you!" +
                "\n" + studentName +
                "\n" + studentId;
        sendEmail(context, recipients, subject, message);
    }

    private static void sendEmail(Activity context, String[] recipients, String subject, String message) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.setType("message/rfc822");
        context.startActivity(Intent.createChooser(emailIntent, "Choose an email account"));
    }

    private static void createEmail(Activity context, EmailType type, Object emailItem) {
        MainApplication appSettings = (MainApplication) context.getApplication();
        switch (type) {
            case EVENT_REGISTRATION:
                sendEventBookingEmail(context, appSettings.getStudentName(), appSettings.getStudentId(), appSettings.getEmail(), (Event) emailItem);
                break;
            case MENTOR_BOOKING:
                sendMentorBookingEmail(context, appSettings.getStudentName(), appSettings.getStudentId(), appSettings.getEmail());
                break;
        }
    }

    public static void showStudentNameDialog(Activity context, EmailType type, Object emailItem) {
        View dialogView = context.getLayoutInflater().inflate(R.layout.dialog_student_information, null);
        TextInputEditText studentNameInput = dialogView.findViewById(R.id.inputStudentName);
        TextInputEditText studentIdInput = dialogView.findViewById(R.id.inputStudentId);
        TextInputEditText emailInput = dialogView.findViewById(R.id.inputEmail);

        AlertDialog emailDialog = new MaterialAlertDialogBuilder(context)
                .setMessage("Enter your name, student number, and Carleton email")
                .setView(dialogView)
                .setPositiveButton("Submit", null)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .create();

        emailDialog.show();

        Button positiveButton = emailDialog.getButton(DialogInterface.BUTTON_POSITIVE);

        positiveButton.setOnClickListener(v -> {
            String studentName = studentNameInput.getText().toString();
            String studentId = studentIdInput.getText().toString();
            String studentEmail = emailInput.getText().toString();
            boolean error = false;

            if(studentName.isEmpty()){
                studentNameInput.setError("Enter your name.");
                error = true;
            }

            if(studentId.isEmpty()){
                studentIdInput.setError("Enter your student number.");
                error = true;
            }else if(!studentId.matches("[0-9]+")){
                studentIdInput.setError("Student number cannot contain text.");
                error = true;
            }

            if(studentEmail.isEmpty()){
                emailInput.setError("Enter your Carleton email");
                error = true;
            }

            if(!error) {
                emailDialog.dismiss();
                MainApplication appSettings = (MainApplication) context.getApplication();
                appSettings.setStudentName(studentNameInput.getText().toString());
                appSettings.setStudentId(studentIdInput.getText().toString());
                appSettings.setEmail(emailInput.getText().toString());
                createEmail(context, type, emailItem);
            }
        });
                emailDialog.show();
    }

    public static void confirmSendEmail(Activity context, EmailType type, Object emailItem) {
        MainApplication appSettings = (MainApplication) context.getApplication();

        new MaterialAlertDialogBuilder(context)
                .setMessage("Do you want to continue to send an email?\n" +
                        "\nName: " + appSettings.getStudentName() +
                        "\nStudent Number: " + appSettings.getStudentId() +
                        "\nCarleton Email: " + appSettings.getEmail()
                )
                .setPositiveButton("Continue", (dialog, which) -> {
                    dialog.dismiss();
                    createEmail(context, type, emailItem);
                })
                .setNegativeButton("Edit", (dialog, which) -> {
                    dialog.cancel();
                    showStudentNameDialog(context, type, emailItem);
                })
                .create()
                .show();
    }
}
