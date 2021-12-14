using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class CloseTurkeyblue : MonoBehaviour
{
    public Button click;
    public GameObject UiObject;
    public Camera script;
    // Start is called before the first frame update
    void Start()
    {
        click.onClick.AddListener(OnClick);
    }

    // Update is called once per frame
    private void OnClick()
    {
        script.GetComponent<ConnectTurkeyblue>().enabled = false;
        UiObject.SetActive(false);
    }
}
