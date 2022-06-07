
export const downloadByFrame = function(url, methods, params) {
  const iframe = document.createElement('iframe')
  iframe.style.display = 'none'
  iframe.name = 'submit_iframe'

  const newUrl = url

  const form = document.createElement('form')
  form.setAttribute('style', 'display:none')
  form.setAttribute('method', methods || 'get')
  form.setAttribute('action', newUrl)
  form.setAttribute('target', 'submit_iframe')


    if (params instanceof Array) {

        params.forEach(param => {

            const tmp = document.createElement('input')

            tmp.name = param.key
            tmp.type = 'hidden'
            tmp.value = param.value

            form.appendChild(tmp)
        })

    }


  iframe.appendChild(form)

  document.body.appendChild(iframe)

  form.submit()
  form.onsubmit = function() {
    document.body.removeChild(iframe)
  }
}
