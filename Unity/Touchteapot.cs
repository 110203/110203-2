using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Touchteapot : MonoBehaviour
{
    public GameObject UiObject;
    public Camera script;
    // Start is called before the first frame update
    void Start()
    {
        UiObject.SetActive(false);
        script.GetComponent<Connectteapot>().enabled = false;
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
                if (hit.transform.tag == "teapot")
                {
                    script.GetComponent<Connectteapot>().enabled = true;
                    UiObject.SetActive(true);
                }
            }
        }

    }
}
