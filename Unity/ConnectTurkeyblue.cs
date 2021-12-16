using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using MySql.Data.MySqlClient;
using UnityEngine.UI;

public class ConnectTurkeyblue : MonoBehaviour
{
    public Text ExTurkeyblueName;
    public Text ExTurkeybluePrice;
    public Text ExTurkeyblueText;
    string id;
    // Start is called before the first frame update
    private void Start()
    {
        string constructorString = "datasource=35.194.169.145;port=3306;database=Gcp110203;user=andy;pwd=a877499a;charset=utf8;";
        MySqlConnection conn2 = new MySqlConnection(constructorString);
        try
        {
            conn2.Open();
            Debug.Log("�H�إ߳s������");
            string sql2 = "select * from goods where gNo='34'";

            MySqlCommand cmd2 = new MySqlCommand(sql2, conn2);

            MySqlDataReader rdr2 = cmd2.ExecuteReader();
            Debug.Log("�Ĥ@�B����");
            if (rdr2.Read())
            {

                ExTurkeyblueName.text = rdr2.GetString("gName");
                ExTurkeybluePrice.text = rdr2.GetString("price") + ("��");
                ExTurkeyblueText.text = rdr2.GetString("introdution");

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
            Debug.Log("����1");
        }
    }
}
