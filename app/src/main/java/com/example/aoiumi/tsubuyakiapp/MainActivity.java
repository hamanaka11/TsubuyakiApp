package com.example.aoiumi.tsubuyakiapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    // つぶやき表示用リストビュー
    private ListView tsubuyakiLV;

    // つぶやき入力欄
    private EditText commentEtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // レイアウトより、つぶやくボタンの情報を取得
        Button commitBtn = (Button) findViewById(R.id.main_commit_btn);
        commitBtn.setOnClickListener(this);

        // レイアウトより、リストビューの情報を取得
        tsubuyakiLV = (ListView) findViewById(R.id.main_tsubuyaki_lv);
        tsubuyakiLV.setOnItemLongClickListener(this);

        // リストビューの内容を更新する
        updateListView();
    }

    @Override
    public void onClick(View view) {
        // レイアウトより、入力欄の情報を取得
        commentEtx = (EditText) findViewById(R.id.main_comment_etx);

        Tsubuyaki tsubuyaki = new Tsubuyaki();
        tsubuyaki.id = 1;
        tsubuyaki.comment = commentEtx.getText().toString() + " にゃー";
        tsubuyaki.save(); // テーブルに保存

        updateListView();
        commentEtx.setText(""); // 入力欄を空にする
    }

    // リストビューの内容を更新する
    private void updateListView() {

        // テーブルからすべてのデータを取得
        List<Tsubuyaki> list = Tsubuyaki.listAll(Tsubuyaki.class);
        // 昇順(ASC): 小さい順
        // 降順(DESC): 大きい順
        list = Tsubuyaki.listAll(Tsubuyaki.class, "ID DESC");

        // リストビューにデータをセット
        // Adapter：特定のデータをひとまとめにしてビューに渡す時に利用する
        AdapterListTsubuyaki adapter =
                new AdapterListTsubuyaki(this, R.layout.list_tsubuyaki, list);
        tsubuyakiLV.setAdapter(adapter);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        // 長押しされたリストビューの項目を取得
        ListView list = (ListView) adapterView;

        // 項目の情報を取得
        final Tsubuyaki selectedItem = (Tsubuyaki) list.getItemAtPosition(i);

        // 警告ダイアログを出す
        new AlertDialog.Builder(this)
                .setTitle("警告")
                .setMessage("削除してもよろしいですか？")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // OK button pressed
                        // テーブルから該当するIDの項目を削除
                        Tsubuyaki tsubuyaki = Tsubuyaki.findById(Tsubuyaki.class, selectedItem.getId());
                        tsubuyaki.delete();

                        // 削除を反映させるためリストビュー更新
                        updateListView();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

        return false;
    }
}