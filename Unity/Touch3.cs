using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Touch3 : MonoBehaviour
{
    public GameObject UiObject;
    public Camera script;
    // Start is called before the first frame update
    void Start()
    {
        UiObject.SetActive(false);
        script.GetComponent<ConnectEx3>().enabled = false;
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.touchCount > 0 && Input.touches[0].phase == TouchPhase.Began)
        {
            Ray ray = Camera.main.ScreenPointToRay(Input.touches[0].position);

            RaycastHit hit;

            if (Physics.Raycast(ray, out hit))
            {
                if (hit.transform.tag == "Ex3")
                {
                    script.GetComponent<ConnectEx3>().enabled = true;
                    UiObject.SetActive(true);
                }
            }
        }

    }
}
