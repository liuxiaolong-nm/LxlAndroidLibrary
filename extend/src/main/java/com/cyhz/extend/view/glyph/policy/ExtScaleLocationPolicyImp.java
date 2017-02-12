package com.cyhz.extend.view.glyph.policy;

import com.cyhz.extend.view.glyph.ExtLineConcrere;

/**
 * Created by liuxiaolong on 17/1/11.
 */
public abstract class ExtScaleLocationPolicyImp implements ExtScaleLocationPolicy{
    protected ExtLineConcrere mExtLineConcrere;
    protected ExtLocationReference mReference;

    public ExtScaleLocationPolicyImp(ExtLineConcrere extXLineConcrere){
        mExtLineConcrere = extXLineConcrere;
        mReference = extXLineConcrere.getLocationReference();
    }

}
