using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using MySql.Data.MySqlClient;
using UnityEngine.UI;
using System;

public class ConnectEx1 : MonoBehaviour
{
    public Text Ex1Name;
    public Text Ex1Price;
    string id;
    // Start is called before the first frame update
    private void Start()
    {
        string constructorString = "datasource=35.194.169.145;port=3306;database=Gcp110203;user=andy;pwd=a877499a;charset=utf8;";
        MySqlConnection conn2 = new MySqlConnection(constructorString);
        try
        {
            conn2.Open();
            Debug.Log("以建立連結測試");
            string sql2 = "select * from goods where gNo='19'";
            
            MySqlCommand cmd2 = new MySqlCommand(sql2, conn2);
            
            MySqlDataReader rdr2 = cmd2.ExecuteReader();
            Debug.Log("第一步完成");
            if (rdr2.Read())
            {

                Ex1Name.text = rdr2.GetString("gName");
                Ex1Price.text = rdr2.GetString("price") + ("元");


            }
            Debug.Log(id);
        }
        catch (MySqlException ex)
        {
            Debug.Log(ex.Message);
        }
        finally
        {
            conn2.Close();
            Debug.Log("關閉1");
        }
    }
}
