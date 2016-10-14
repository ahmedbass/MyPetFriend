package com.ahmedbass.mypetfriend;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// i took the content in this class almost as it is from an online tutorial (with few modifications)

//---In SendMailActivity.java – Activity to Compose and Send Email---
public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button sendPassword_btn = (Button) findViewById(R.id.sendPassword_btn);
        sendPassword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SendMailActivity will receive the user email arguments and pass it on to the SendMailTask to send email
                String fromEmail = "dummy@gmail.com";
                String fromPassword = "123456";
                String toEmails = ((TextView) findViewById(R.id.email_etxt)).getText().toString();
                if(!toEmails.contains("@") || toEmails.startsWith("@") || !(toEmails.endsWith(".com") || toEmails.endsWith(".net")
                        || toEmails.endsWith(".edu") || toEmails.endsWith(".org") || toEmails.endsWith(".gov"))){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    List<String> toEmailList = Arrays.asList(toEmails.split("\\s*,\\s*"));
                    String emailSubject = "My PetFriend: Retrieve Your Password";
                    String emailBody = "Hello Mr. Someone, you requested your password, here it is:\n" + "password"; //TODO change this to send proper password
                    new SendMailTask(ForgotPasswordActivity.this).execute(fromEmail, fromPassword, toEmailList, emailSubject, emailBody);
                }
            }
        });
    }

//------------------------------------------------------------------------------------------------
    //---In SendMailTask.java – AsyncTask for Sending Email---
    //SendMailTask interacts with GMail.java by passing parameters to and publishing mail sending status using ProgressDialog.
    private class SendMailTask extends AsyncTask {

        private ProgressDialog statusDialog;
        private Activity sendMailActivity;

        private List<String> emailTo;

        SendMailTask(Activity activity) {
            sendMailActivity = activity;
        }

        protected void onPreExecute() {
            statusDialog = new ProgressDialog(sendMailActivity);
            statusDialog.setMessage("Getting ready...");
            statusDialog.setIndeterminate(false);
            statusDialog.setCancelable(false);
            statusDialog.show();
        }

        @Override
        protected Object doInBackground(Object... args) {
            try {
                emailTo = (List) args[2];
                Log.i("SendMailTask", "About to instantiate GMail...");
                publishProgress("Processing input...");
                GMail androidEmail = new GMail(args[0].toString(), args[1].toString(),
                        (List) args[2], args[3].toString(), args[4].toString());
                publishProgress("Preparing mail message...");
                androidEmail.createEmailMessage();
                publishProgress("Sending email...");
                androidEmail.sendEmail();
                publishProgress("Email Sent.");
                Log.i("SendMailTask", "Mail Sent Successfully.");
                return true;
            } catch (Exception e) {
                publishProgress(e.getMessage());
                Log.e("SendMailTask", e.getMessage(), e);
                return e.getMessage();
            }
//            return null;
        }

        @Override
        public void onProgressUpdate(Object... values) {
            try {
                statusDialog.setMessage(values[0].toString());
            } catch (Exception e) { }
        }

        @Override
        public void onPostExecute(final Object result) {
            statusDialog.setCancelable(true);
            if(result instanceof Boolean && result.equals(true)) {
                statusDialog.setMessage("Email sent successfully to " + emailTo);
            } else {
                if(result instanceof String) {
                    statusDialog.setMessage("Error: " + result + "\nPlease try again");
                }
            }

            // Hide dialog after some seconds
            final Handler handler  = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (statusDialog.isShowing()) { statusDialog.dismiss(); }
                    if(result instanceof Boolean && result.equals(true)) { sendMailActivity.finish(); }
                }
            };
            statusDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    handler.removeCallbacks(runnable);
                }
            });
            handler.postDelayed(runnable, 2500);
        }
    }
}

//------------------------------------------------------------------------------------------------
//---In GMail.java – GMail Email Sender using JavaMail---

//Constructor sets the basic required JavaMail parameters and configuration settings for GMail SMTP.
//Constructs the MimeMessage using which the email will be sent.
//Sends email using Transport API.

class GMail {

    private final String emailPort = "587";// gmail's SMTP port
    private final String smtpAuth = "true";
    private final String starttls = "true";
    private final String emailHost = "smtp.gmail.com";

    private String fromEmail;
    private String fromPassword;
    private List<String> toEmailList;
    private String emailSubject;
    private String emailBody;

    private Properties emailProperties;
    private Session mailSession;
    private MimeMessage emailMessage;

    public GMail() { }

    GMail(String fromEmail, String fromPassword, List<String> toEmailList, String emailSubject, String emailBody) {
        this.fromEmail = "mypetfriendapp@gmail.com";
        this.fromPassword = "123456_Mypetfriend";
        this.toEmailList = toEmailList;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", smtpAuth);
        emailProperties.put("mail.smtp.starttls.enable", starttls);
    }

    MimeMessage createEmailMessage() throws MessagingException, UnsupportedEncodingException {
        mailSession = Session.getInstance(emailProperties, new GMailAuthenticator(fromEmail, fromPassword));
        emailMessage = new MimeMessage(mailSession);
        emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));

        for (String toEmail : toEmailList) {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        }

        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html"); //for a html email
        //emailMessage.setText(emailBody); //for a text email
        return emailMessage;
    }

    void sendEmail() throws MessagingException {
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, fromEmail, fromPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
    }


    //---We need to implement this class for security reasons--- (i think?)
    private class GMailAuthenticator extends Authenticator {
        private String user;
        private String password;
        GMailAuthenticator(String username, String password) {
            super();
            this.user = username;
            this.password = password;
        }
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, password);
        }
    }
}